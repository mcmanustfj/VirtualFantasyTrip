package vft.window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;

import vft.GameChar;
import vft.VFTChar;
import vft.VFTTeam;
import vft.map.FractionalHex;
import vft.map.Hex;
import vft.map.Layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel {

    private VFTTeam[] teams;
    private VFTTeam activeTeam;
    private List<GameChar> activeGameTeam;
    private GameChar activeChar;
    private JList<GameChar> infoPanelList;
    private DefaultListModel<GameChar> listModel;
    private Layout map;
    private Point2D o;
    private Point2D p;
    private Map<VFTTeam, ArrayList<GameChar>> gameTeams;
    private final Color TEAMCOLOR = Color.BLUE;
    private final Color ENEMYCOLOR = Color.RED;

    private boolean isRunning = false, moveInProgress = false;
    int turn = 0;
    Map<Hex, Integer> moveableTiles;
    Map<Hex, Map.Entry<Hex, Integer>> attackableTiles;

    /**
     * Create the panel.
     */
    public GamePanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{180, 89, 0};
        gridBagLayout.rowHeights = new int[]{23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        JButton btnEndTurn = new JButton("End Turn");
        btnEndTurn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    endMove();
                    nextTurn();
                    repaint();
                }
            });
        GridBagConstraints gbc_btnEndTurn = new GridBagConstraints();
        gbc_btnEndTurn.insets = new Insets(0, 0, 0, 5);
        gbc_btnEndTurn.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnEndTurn.gridx = 0;
        gbc_btnEndTurn.gridy = 9;
        add(btnEndTurn, gbc_btnEndTurn);

        listModel = new DefaultListModel<GameChar>();

        map = Layout.genPgram(2); //just for window builder and initialization
        map.setOrigin(new Point2D.Double(getWidth()/2, getHeight()/2));
        o = map.getOrigin();
        MouseAdapter ma = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if(SwingUtilities.isRightMouseButton(e)) {
                        p = e.getPoint();
                        if(e.isShiftDown()) {
                            o = p;
                            map.setOrigin(p);
                            repaint();
                        }
                    }
                    else {
                        Hex hex = map.getHex(map.pixelToHex(e.getPoint()));
                        if(hex == null) return;
                        if(!moveInProgress) {
                            if(!activeGameTeam.contains(hex.getGameChar())) {
                                map.focus(hex);
                            }
                            else {
                                GameChar c = hex.getGameChar();
                                selectCharacter(c);
                                System.out.println("character selected");

                            }
                        }
                        else {
                            if(hex == null) return;
                            else {
                                makeMove(hex);
                                System.out.println("made move");
                                endMove();
                            }
                        }
                        repaint();
                    }
                }

                public void mouseReleased(MouseEvent e) {
                    o = map.getOrigin();
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    if(SwingUtilities.isRightMouseButton(e)) {
                        double dx = e.getX() - p.getX();
                        double dy = e.getY() - p.getY();

                        map.setOrigin(new Point2D.Double(o.getX() + dx, o.getY() + dy));

                        dx = 0;
                        dy = 0;
                        repaint();
                    }
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    Hex hex = map.pixelToHex(e.getPoint());
                    for(Hex h : map.getMap()) {
                        h.setSelected(h.equals(hex));
                    }
                    repaint();
                }

            };
        addMouseMotionListener(ma);
        addMouseListener(ma);
    }

    public void setCharacters() {
        for(int i = 0; i < teams.length; i++) {
            List<GameChar> chars = gameTeams.get(teams[i]);
            Hex startingPosition = map.getStartingPositions()[i];
            Set<Hex> positions =  startingPosition.range(1, map);
            for(int r = 2; chars.size() > positions.size(); r++) {
                positions = startingPosition.range(r, map);
            }
            int j = 0;
            for(Hex h : positions) {
                if (j<chars.size()) {
                    h.setGameChar(chars.get(j));
                    j++;
                }
            }
        }
    }

    public void startGame() {

        arrayShuffle(teams);
        nextTurn();
    }

    

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        map.paint(g2d);
    }

    public VFTTeam[] getTeams() {
        return teams;
    }
    
    public void selectCharacter(GameChar c) {
        if(c == null) {
            System.err.println("null char");
            return;
        }
        endMove();
        map.focus(c.getPosition());
        activeChar = c;
        infoPanelList.setSelectedValue(c, true);
        moveableTiles = getMoveableTiles(c);
        attackableTiles = getAttackableTiles(moveableTiles); 
        map.highlightHexes(moveableTiles.keySet(), TEAMCOLOR);
        map.highlightHexes(attackableTiles.keySet(), ENEMYCOLOR);
        System.out.println(attackableTiles.keySet());
        moveInProgress = true;

        repaint();
    }
    
    public void makeMove(Hex hex) {
        if(attackableTiles.keySet().contains(hex)) {
            Hex old = map.getHex(activeChar.getPosition());
            old.setGameChar(null);
            map.getHex(hex).setGameChar(activeChar);
            activeChar.attack(attackableTiles.get(hex).getKey().getGameChar());
            activeChar.setMoves(0);
            System.out.println("should attack");
        }
        else if(moveableTiles.keySet().contains(hex)) {
            Hex old = map.getHex(activeChar.getPosition());
            old.setGameChar(null);
            map.getHex(hex).setGameChar(activeChar);
            activeChar.setMoves(activeChar.getMoves() - moveableTiles.get(hex));
            System.out.println("won't attack");
        }
        else {
            System.out.println("Illegal move");
        }
    }

    public void endMove() {
        moveInProgress = false;
        map.focus(null);
        map.highlightHexes(map.getMap(), null);
        repaint();
    }
    
    public void nextTurn() {
        turn++;
        activeGameTeam = gameTeams.get(teams[turn % teams.length]);
        listModel.clear();
        for(List<GameChar> list : gameTeams.values()) {
            for(GameChar c : list) {
                if(list.equals(activeGameTeam)) {
                    c.setColor(TEAMCOLOR);
                    listModel.addElement(c);
                }
                else {
                    c.setColor(ENEMYCOLOR);
                }
                c.refresh();
            }
        }
        if(gameIsWon()) endGame();

    }


    public void setTeams(VFTTeam[] teams) {
        this.teams = teams;
        gameTeams = new HashMap<>();
        for(int i = 0; i < teams.length; i++) {
            gameTeams.put(teams[i], new ArrayList<>());
            for(VFTChar c : teams[i].getChars()) {
                gameTeams.get(teams[i]).add(new GameChar(c, map));
            }
        }
    }


    public JList<GameChar> getInfoPanelList() {
        return infoPanelList;
    }

    public void setActiveTeam(VFTTeam team) {
        activeTeam = team;
        activeGameTeam = gameTeams.get(team);
        listModel.clear();
        for(GameChar c : activeGameTeam) {
            listModel.addElement(c);
        }
    }

    public void setInfoPanelList(JList<GameChar> infoPanelList) {
        this.infoPanelList = infoPanelList;
        listModel = new DefaultListModel<GameChar>();
        infoPanelList.setModel(listModel);
        infoPanelList.setCellRenderer(new GameCharInfoPanelRenderer<GameChar>());
        infoPanelList.addListSelectionListener(new ListSelectionListener() {

                @Override
                public void valueChanged(ListSelectionEvent arg0) {
                    if(!infoPanelList.getValueIsAdjusting()){
                        activeChar = infoPanelList.getSelectedValue();
                        moveInProgress = false;
                        selectCharacter(activeChar);
                    }
                }

            });

    }


    public Layout getMap() {
        return map;
    }


    public void setMap(Layout layout) {
        this.map = layout;
        map.setOrigin(new Point2D.Double(getWidth()/2, getHeight()/2));
        o = map.getOrigin();

    }

    public boolean gameIsWon(){
        return false;
    }

    public void endGame() {

    }

    private void arrayShuffle(Object[] array)
    {
        int index;
        Object temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private boolean hasMoves(ArrayList<GameChar> list) {
        for(GameChar c : list) {
            if(c.getMoves() > 0) return true;
        }
        return false;
    }

    

    public Map<Hex, Integer> getMoveableTiles(GameChar c) {
        Map<Hex, Integer> inactive = new HashMap<>();
        Map<Hex, Integer> active = new HashMap<>();

        active.put(map.getHex(c.getPosition()), 0);
        for(int i = 1; i < c.getMoves(); i++) { //i is the number of "steps" the character will have to take
            Map<Hex, Integer> newActive = new HashMap<>();
            for(Hex hex : active.keySet()) {
                for (Hex n : hex.getNeighbors()) {
                    n = map.getHex(n);
                    if(!active.containsKey(n) && !inactive.containsKey(n) && map.getHex(n) != null && n.getGameChar() == null) {
                        newActive.put(map.getHex(n), i);
                    }
                }
            }
            for(Hex h : active.keySet()) {
                if(!inactive.containsKey(map.getHex(h))) {
                    inactive.put(h, active.get(h));
                }
            }
            active = newActive;
        }

        return inactive;
    }

    public Map<Hex, Map.Entry<Hex, Integer>> getAttackableTiles(Map<Hex, Integer> moveableTiles) {
        Map<Hex, Map.Entry<Hex, Integer>> enemyTiles = new HashMap<>();
        for(Map.Entry<Hex, Integer> entry : moveableTiles.entrySet()) {
            for (Hex n : entry.getKey().getNeighbors()) {
                n = map.getHex(n);
                if(n == null) continue;
                if(n.getGameChar() != null && !activeGameTeam.contains(n.getGameChar())) {
                    if(!enemyTiles.containsKey(n)) {
                        enemyTiles.put(n, entry);
                        System.out.println("added attackable tile");
                    }
                }
            }
        }
        return enemyTiles;
    }

}
