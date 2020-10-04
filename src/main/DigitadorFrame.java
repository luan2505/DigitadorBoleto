package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javafx.scene.paint.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class DigitadorFrame extends JFrame {

	private DigitarBoleto db = new DigitarBoleto();
	private JPanel contentPane;
	private JTextField txtBoletoCode;

	private int delayValue[] = { 5000, 4000, 3000, 2000 };
	private int sizeBoleto = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DigitadorFrame frame = new DigitadorFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DigitadorFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblCabecalho = new JLabel("Digitador de Boleto");
		lblCabecalho.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCabecalho.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblAtual = new JLabel("");

		// CAMPO DE TEXTO DO BOLETO ---------------------------------------------
		txtBoletoCode = new JTextField();
		txtBoletoCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				// Tradando CTRL + C / CTRL + V
				// ------------------------------------------------------------------------------------------------
				if (e.getKeyCode() == KeyEvent.VK_V && e.isControlDown()) {
					String copia;
					try {
						copia = (String) Toolkit.getDefaultToolkit().getSystemClipboard()
								.getData(DataFlavor.stringFlavor);

						copia = copia.replaceAll("\\D+", "");
						txtBoletoCode.replaceSelection("");
						txtBoletoCode.setText(txtBoletoCode.getText() + copia);

						sizeBoleto = txtBoletoCode.getText().length();

						textoDeValidacao(lblAtual, sizeBoleto);
						e.consume();

					} catch (HeadlessException | UnsupportedFlavorException | IOException e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				sizeBoleto = txtBoletoCode.getText().length();
				textoDeValidacao(lblAtual, sizeBoleto);
				System.out.println(sizeBoleto);
			}

			public void textoDeValidacao(JLabel lblAtual, int txtSize) {

				if (txtSize == 44) {
					lblAtual.setForeground(new java.awt.Color(33, 200, 33));
					lblAtual.setText("Tamanho Valido");
				} else if (txtSize > 44) {
					lblAtual.setForeground(new java.awt.Color(200, 33, 33));
					lblAtual.setText("Tamanho Maior que de um BOLETO");
				} else {
					lblAtual.setText("");
				}
			}
		});
		txtBoletoCode.setToolTipText("Cole o Boleto");
		txtBoletoCode.setColumns(10);

		JSeparator separator = new JSeparator();

		JSeparator separator_1 = new JSeparator();

		JLabel lblNewLabel = new JLabel("Digite o numero do Boleto:");

		JComboBox cboxDelay = new JComboBox();
		cboxDelay.setModel(new DefaultComboBoxModel(new String[] { "5 seg", "4 seg", "3 seg", "2 seg" }));

		// BOTAO QUE APLICA A DIGITACAO ---------------------------------------------
		JButton btnDigitarBoleto = new JButton("Digitar Boleto");
		btnDigitarBoleto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = txtBoletoCode.getText();

				if (txt.equals("") || txt.equals(null)) {
					return;
				}

				int delayInit = delayValue[cboxDelay.getSelectedIndex()];
				System.out.println(cboxDelay.getSelectedIndex());
				db.DigitarInput(txt, delayInit, 25);
			}
		});
		btnDigitarBoleto.setFont(new Font("Tahoma", Font.BOLD, 11));

		JLabel lblNewLabel_1 = new JLabel("Delay para iniciar:");

		lblAtual.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_2 = new JLabel("by Luan Cruz - Versao 0.2");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnDigitarBoleto, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(separator, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(lblCabecalho, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(lblAtual, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(txtBoletoCode, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cboxDelay, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
							.addGap(8))
						.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblCabecalho)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGap(62)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1)
						.addComponent(cboxDelay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtBoletoCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAtual, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDigitarBoleto, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
