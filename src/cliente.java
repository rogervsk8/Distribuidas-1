import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class cliente {

    private BufferedReader in;
    private PrintWriter out;
    private JFrame frame = new JFrame("Cliente");
    private JTextField dataField = new JTextField(40);
    private JTextArea messageArea = new JTextArea(8, 60);

    public cliente() {

        messageArea.setEditable(false);
        frame.getContentPane().add(dataField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");

        dataField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                out.println(dataField.getText());
                   String response;
                try {
                    response = in.readLine();
                    if (response == null || response.equals("")) {
                          System.exit(0);
                      }
                } catch (IOException ex) {
                       response = "Error: " + ex;
                }
                messageArea.append(response + "\n");
                dataField.selectAll();
            }
        });
    }


    public void connectToServer() throws IOException {

        String serverAddress = JOptionPane.showInputDialog(
            frame,
            "Ingrese la direccion IP del servidor:",
            "Bienvenido ",
            JOptionPane.QUESTION_MESSAGE);

        Socket socket = new Socket(serverAddress, 9999);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        for (int i = 0; i < 3; i++) {
            messageArea.append(in.readLine() + "\n");
        }
    }


    public static void main(String[] args) throws Exception {
        cliente client = new cliente();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.pack();
        client.frame.setVisible(true);
        client.connectToServer();
    }
}