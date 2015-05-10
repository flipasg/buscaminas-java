/**
 *
 */
package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import juego.Juego;

/**
 * @author Iker Garcia Ramirez
 * @date 9/5/2015
 *
 */
public class VentanaBuscaminas extends JFrame implements ActionListener {
    private Juego j;
    private JPanel minas;
    private JMenuBar menu;
    private JMenuItem reiniciar, salir, basico, intermedio, avanzado,
	    personalizado;
    private JButton[][] botones;

    public VentanaBuscaminas(String titulo) {
	super(titulo);
	iniciar();
    }

    private void iniciar() {
	instanciar();
	configurar(0);
	aniadir();
	oyentes();
	propiedades();
    }

    private void instanciar() {

	menu = new JMenuBar();
	JMenu inicio = new JMenu("Inicio");
	reiniciar = new JMenuItem("Reiniciar");
	salir = new JMenuItem("Salir");
	JMenu dificultad = new JMenu("Dificultad");
	basico = new JMenuItem("Basico");
	intermedio = new JMenuItem("Intermedio");
	avanzado = new JMenuItem("Avanzado");
	personalizado = new JMenuItem("Personalizado");
	inicio.add(reiniciar);
	inicio.add(salir);

	dificultad.add(basico);
	dificultad.add(intermedio);
	dificultad.add(avanzado);
	dificultad.add(personalizado);
	minas = new JPanel(new GridLayout());
	menu.add(inicio);
	menu.add(dificultad);

    }

    private void configurar(int dificultad) {
	if (dificultad < 4) {
	    j = new Juego(dificultad);
	} else {
	    String f = JOptionPane.showInputDialog(getContentPane(),
		    "Dime el numero de filas");
	    int filas = Integer.parseInt(f);
	    String c = JOptionPane.showInputDialog(getContentPane(),
		    "Dime el numero de columnas");
	    int columnas = Integer.parseInt(c);
	    String b = JOptionPane.showInputDialog(getContentPane(),
		    "Dime el numero de bombas");
	    int bombas = Integer.parseInt(b);

	    j = new Juego(filas, columnas, bombas);

	}

	int w = j.getColumnas() * 50;
	int h = j.getFilas() * 50;
	setSize(new Dimension(w, h));
	botones = new JButton[j.getFilas()][j.getColumnas()];
	minas.setLayout(new GridLayout(j.getFilas(), j.getColumnas()));
	EscuchaClickDerecho ecd = new EscuchaClickDerecho();
	for (int i = 0; i < j.getFilas(); i++) {
	    for (int j1 = 0; j1 < j.getColumnas(); j1++) {
		botones[i][j1] = new JButton();
		botones[i][j1].addActionListener(this);
		botones[i][j1].addMouseListener(ecd);
		minas.add(botones[i][j1]);
	    }
	}

    }

    private void aniadir() {
	add("North", menu);
	add("Center", minas);
    }

    private void oyentes() {
	basico.addActionListener(this);
	intermedio.addActionListener(this);
	avanzado.addActionListener(this);
	personalizado.addActionListener(this);
	reiniciar.addActionListener(this);
	salir.addActionListener(this);
    }

    private void propiedades() {
	setVisible(true);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setResizable(false);
    }

    public static void main(String[] args) {
	new VentanaBuscaminas("BUSCAMINAS");
    }

    private void reiniciarTablero() {
	for (int i = 0; i < j.getFilas(); i++) {
	    for (int j1 = 0; j1 < j.getColumnas(); j1++) {
		if (botones[i][j1].getIcon() != null) {
		    botones[i][j1].setIcon(null);
		}
		if (!botones[i][j1].getText().equals("")) {
		    botones[i][j1].setText("");
		}
		botones[i][j1].setEnabled(true);
	    }
	}
	j.setBombasAcertadas(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource().getClass() != JButton.class) {
	    if (e.getSource() == basico) {
		if (j.getDificultad() == 0) {
		    reiniciarTablero();
		    j.colocarMinas();
		} else {
		    minas.removeAll();
		    configurar(0);
		}

	    }
	    if (e.getSource() == intermedio) {
		if (j.getDificultad() == 1) {
		    reiniciarTablero();
		    j.colocarMinas();
		} else {
		    minas.removeAll();
		    configurar(1);
		}
	    }
	    if (e.getSource() == avanzado) {
		if (j.getDificultad() == 2) {
		    reiniciarTablero();
		    j.colocarMinas();
		} else {
		    minas.removeAll();
		    configurar(2);
		}
	    }
	    if (e.getSource() == personalizado) {
		if (j.getDificultad() == 4) {
		    reiniciarTablero();
		    j.colocarMinas();
		} else {
		    minas.removeAll();
		    configurar(4);
		}
	    }
	    if (e.getSource() == reiniciar) {
		reiniciarTablero();
	    }
	    if (e.getSource() == salir) {
		dispose();
	    }
	} else {
	    boolean encontrado = false;
	    int x = 0;
	    int y = 0;
	    JButton b = null;
	    for (int i = 0; !encontrado && i < botones.length; i++) {
		for (int j = 0; !encontrado && j < botones[i].length; j++) {
		    if (botones[i][j] == e.getSource()) {
			encontrado = true;
			b = botones[i][j];
			x = j;
			y = i;
		    }
		}
	    }
	    if (b.getIcon() == null) {
		if (j.esMina(y, x)) {
		    for (int i = 0; i < j.getFilas(); i++) {
			for (int j1 = 0; j1 < j.getColumnas(); j1++) {
			    if (j.getTablero()[i][j1] == -1) {
				botones[i][j1]
					.setIcon(redimensionarIcono("img/bomba.gif"));

			    }
			}
		    }
		} else if (j.esNumero(y, x)) {
		    b.setText(Integer.toString(j.getTablero()[y][x]));
		} else {

		    expandir(y, x);
		}
	    }

	}

    }

    public void expandir(int y, int x) {

	if (j.esNumero(y, x)) {
	    botones[y][x].setText(Integer.toString(j.getTablero()[y][x]));
	} else {
	    botones[y][x].setEnabled(false);
	    // contadores superiores
	    if ((y - 1) >= 0 && (x - 1) >= 0
		    && j.getTablero()[y - 1][x - 1] == 0) {
		expandir(y - 1, x - 1);
	    }
	    if ((y - 1) >= 0 && (x + 1) < j.getColumnas()
		    && j.getTablero()[y - 1][x + 1] == 0) {
		expandir(y - 1, x + 1);
	    }
	    if ((y - 1) >= 0 && j.getTablero()[y - 1][x] == 0) {
		expandir(y - 1, x);
	    }
	    // contadores inferiores
	    if ((y + 1) < j.getFilas() && (x - 1) >= 0
		    && j.getTablero()[y + 1][x - 1] == 0) {
		expandir(y + 1, x - 1);
	    }
	    if ((y + 1) < j.getFilas() && (x + 1) < j.getColumnas()
		    && j.getTablero()[y + 1][x + 1] == 0) {
		expandir(y + 1, x + 1);
	    }
	    if ((y + 1) < j.getFilas() && j.getTablero()[y + 1][x] == 0) {
		expandir(y + 1, x);
	    }

	    // contadores lados
	    if ((x + 1) < j.getColumnas() && j.getTablero()[y][x + 1] == 0) {
		expandir(y, x + 1);
	    }
	    if ((x - 1) >= 0 && j.getTablero()[y][x - 1] == 0) {
		expandir(y, x - 1);
	    }
	}

    }

    public ImageIcon redimensionarIcono(String icono) {
	ImageIcon icon = new ImageIcon(icono); // creamos un icono con la
	// seleccion
	Image i = icon.getImage(); // creamos una imagen con el icono
	Image otrai = i.getScaledInstance(48, 48, java.awt.Image.SCALE_SMOOTH); // redimensionamos
	// la
	// imagen
	ImageIcon icon2 = new ImageIcon(otrai); // creamos un icono
	// redimensionado

	return icon2;
    }

    private class EscuchaClickDerecho extends MouseAdapter {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	    if (e.getButton() == 3) {
		boolean encontrado = false;
		int x = 0;
		int y = 0;
		JButton b = null;
		for (int i = 0; !encontrado && i < botones.length; i++) {
		    for (int j = 0; !encontrado && j < botones[i].length; j++) {
			if (botones[i][j] == e.getSource()) {
			    encontrado = true;
			    b = botones[i][j];
			    x = j;
			    y = i;
			}
		    }
		}
		if (b.getIcon() == null && b.getText().equals("")) {
		    b.setIcon(redimensionarIcono("img/bandera.jpg"));
		    if (j.esMina(y, x))
			j.incrementarBombasAcertadas();
		} else {
		    b.setIcon(null);
		    if (j.esMina(y, x))
			j.decrementarBombasAcertadas();
		}

		if (j.completo()) {
		    for (int i = 0; i < j.getFilas(); i++) {
			for (int j1 = 0; j1 < j.getColumnas(); j1++) {
			    if (j.getTablero()[i][j1] != 0) {
				botones[i][j1].setText(Integer.toString(j
					.getTablero()[i][j1]));
			    }
			    botones[i][j1].setEnabled(false);
			}
		    }
		    JOptionPane.showMessageDialog(getContentPane(),
			    "HAS GANADO", "ENHORABUENA",
			    JOptionPane.INFORMATION_MESSAGE);
		    configurar(0);
		}

	    }
	}
    }
}
