package main;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {

    private JButton playButton, volumeButton, tutorialButton, resolutionButton;

    public Menu() {
        setLayout(new BorderLayout());

        // Cargar imagen de fondo usando getResource
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/resources/menu_background.png"));
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new GridBagLayout());
        add(backgroundLabel);

        // Título
        JLabel titleLabel = new JLabel("Fútbol Estrellas");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundLabel.add(titleLabel, gbc);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setOpaque(false);

        playButton = createStyledButton("Jugar");
        buttonPanel.add(playButton);

        volumeButton = createStyledButton("Volumen");
        buttonPanel.add(volumeButton);

        resolutionButton = createStyledButton("Resolución");
        buttonPanel.add(resolutionButton);

        tutorialButton = createStyledButton("Tutorial");
        buttonPanel.add(tutorialButton);

        gbc.gridy = 1;
        backgroundLabel.add(buttonPanel, gbc);

        // Asociar acciones a los botones
        setButtonActions();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setBackground(new Color(0, 128, 0));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(34, 139, 34));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 128, 0));
            }
        });

        return button;
    }

    private void setButtonActions() {
        playButton.addActionListener(e -> showTeamAndFormationSelection());
        volumeButton.addActionListener(e -> showVolumeOptions());
        resolutionButton.addActionListener(e -> showResolutionOptions());
        tutorialButton.addActionListener(e -> showTutorial());
    }

    private void showTeamAndFormationSelection() {
        String[] teams = {"Equipo A", "Equipo B", "Equipo C"};
        String[] formations = {"1-3-2", "1-2-3", "1-4-1"};

        // First, select teams
        JComboBox<String> team1Selector = new JComboBox<>(teams);
        JComboBox<String> team2Selector = new JComboBox<>(teams);

        JPanel teamPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        teamPanel.add(new JLabel("Equipo Jugador 1:"));
        teamPanel.add(team1Selector);
        teamPanel.add(new JLabel("Equipo Jugador 2:"));
        teamPanel.add(team2Selector);

        int teamResult = JOptionPane.showConfirmDialog(this, teamPanel, "Seleccionar Equipos",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (teamResult == JOptionPane.OK_OPTION) {
            String selectedTeam1 = (String) team1Selector.getSelectedItem();
            String selectedTeam2 = (String) team2Selector.getSelectedItem();

            if (selectedTeam1.equals(selectedTeam2)) {
                JOptionPane.showMessageDialog(this, "Los equipos no pueden ser iguales.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Then, select formations
            JComboBox<String> formation1Selector = new JComboBox<>(formations);
            JComboBox<String> formation2Selector = new JComboBox<>(formations);

            JPanel formationPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            formationPanel.add(new JLabel("Formación Equipo " + selectedTeam1 + ":"));
            formationPanel.add(formation1Selector);
            formationPanel.add(new JLabel("Formación Equipo " + selectedTeam2 + ":"));
            formationPanel.add(formation2Selector);

            int formationResult = JOptionPane.showConfirmDialog(this, formationPanel, "Seleccionar Formaciones",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (formationResult == JOptionPane.OK_OPTION) {
                String selectedFormation1 = (String) formation1Selector.getSelectedItem();
                String selectedFormation2 = (String) formation2Selector.getSelectedItem();

                System.out.println("Jugador 1: Equipo " + selectedTeam1 + ", Formación " + selectedFormation1);
                System.out.println("Jugador 2: Equipo " + selectedTeam2 + ", Formación " + selectedFormation2);
                startGame();
            }
        }
    }

    private void startGame() {
        // Abrir la ventana del juego
        JFrame gameFrame = new JFrame("Fútbol Estrellas - Juego");
        GamePanel gamePanel = new GamePanel();

        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.add(gamePanel);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);

        gamePanel.startGame();

        // Cerrar el menú principal
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame != null) {
            topFrame.dispose();
        }
    }

    private void showVolumeOptions() {
        JSlider volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);

        int result = JOptionPane.showConfirmDialog(this, volumeSlider, "Configuración de Volumen", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int volume = volumeSlider.getValue();
            System.out.println("Volumen ajustado a: " + volume);
        }
    }

    private void showResolutionOptions() {
        String[] resolutions = {"800x600", "1024x768", "1280x720", "1600x900", "1920x1080"};
        String selectedResolution = (String) JOptionPane.showInputDialog(this, "Selecciona la resolución:",
                "Configuración de Resolución", JOptionPane.PLAIN_MESSAGE, null, resolutions, resolutions[0]);

        if (selectedResolution != null) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            switch (selectedResolution) {
                case "800x600" -> setPreferredSize(new Dimension(800, 600));
                case "1024x768" -> setPreferredSize(new Dimension(1024, 768));
                case "1280x720" -> setPreferredSize(new Dimension(1280, 720));
                case "1600x900" -> setPreferredSize(new Dimension(1600, 900));
                case "1920x1080" -> setPreferredSize(new Dimension(1920, 1080));
            }
            if (topFrame != null) {
                topFrame.pack();
                topFrame.setLocationRelativeTo(null); // Centrar la ventana
            }
        }
    }

    private void showTutorial() {
        String tutorialText = """
                Este es un tutorial para jugar.
                1. Usa el mouse para seleccionar un jugador.
                2. Arrastra para definir la dirección y fuerza del tiro.
                3. Usa el botón de Jugar para comenzar.
                ¡Diviértete!""";
        JOptionPane.showMessageDialog(this, tutorialText, "Tutorial", JOptionPane.INFORMATION_MESSAGE);
    }
}
