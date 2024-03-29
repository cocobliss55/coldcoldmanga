import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ReturnView extends JFrame {

    private Borrow borrowModel = new Borrow();
    private Connection connection;

    public ReturnView()
    {
        try{
            connection = Config.connectDB();
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        setTitle("Return Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create a panel for the borrow form
        JPanel returnPanel = new JPanel(new FlowLayout());

        JTextField bookID = new JTextField(20);

        returnPanel.add(createPanel("Book ID", bookID));

        //Button
        JButton returnBtn = new JButton("Return");
        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bID = bookID.getText();
                double penalty = validateReturn(bID);

                bookID.setText("");

                if(penalty > 0)
                {
                    JOptionPane.showMessageDialog(ReturnView.this, "You will need to pay RM" + penalty + " in total");
                }
                else
                {
                    JOptionPane.showMessageDialog(ReturnView.this, "Returned Successfully");
                }
            }
        });
        returnPanel.add(returnBtn);

        JPanel homepage = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton homeBtn = new JButton("Home");
        homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Home homepage = new Home();
            }
        });
        homepage.add(homeBtn);

        setLayout(new BorderLayout());
        add(returnPanel, BorderLayout.CENTER);
        add(homepage,BorderLayout.NORTH);

        setLocationRelativeTo(null);
        setVisible(true);

    }


    private JPanel createPanel(String label, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.add(new JLabel(label));
        panel.add(component);
        return panel;
    }

    private double validateReturn(String bookID)
    {
        return borrowModel.validateReturn(bookID);
    }
}
