package com.xhc.test.javaapi.net.url;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Post extends JPanel implements ActionListener {

    JTextField nameField, passwordField;
    String postURL;
    
    GridBagConstraints constraints = new GridBagConstraints();
    
    void addGB(Component component, int x, int y) {
        constraints.gridx = x;
        constraints.gridy = y;
        add(component, constraints);
    }
    
    public Post(String postURL) {
        this.postURL = postURL;
        
        setBorder(BorderFactory.createEmptyBorder(5 , 10, 5, 5));
        JButton postButton = new JButton("Post");
        postButton.addActionListener(this);
        setLayout(new GridBagLayout());
        constraints.fill = GridBagConstraints.HORIZONTAL;
        addGB(new JLabel("Name", JLabel.TRAILING), 0, 0);
        addGB(nameField = new JTextField(20), 1, 0);
        addGB(new JLabel("Password", JLabel.TRAILING), 0, 1);
        addGB(passwordField = new JPasswordField(20), 1 ,1);
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.EAST;
        addGB(postButton, 1, 2);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        postData();
    }

    
    protected void postData() {
        StringBuffer sb = new StringBuffer();
        sb.append(URLEncoder.encode("username") + "=");
        sb.append(URLEncoder.encode(nameField.getText()));
        sb.append("&" + URLEncoder.encode("password") + "=") ;
        sb.append(URLEncoder.encode(passwordField.getText()));
        String formData = sb.toString();
        
        try {
            URL url = new URL(postURL);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            urlcon.setRequestMethod("POST");
            urlcon.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            urlcon.setDoOutput(true);
            urlcon.setDoInput(true);
            PrintWriter pout = new PrintWriter(new OutputStreamWriter(urlcon.getOutputStream(), "8859_1"), true);
            pout.print(formData);
            pout.flush();
            
            if(urlcon.getResponseCode() == HttpURLConnection.HTTP_OK){
                System.out.println("Poseted OK!");
            }else {
                System.out.println("Bad post...");
                return;
            }
            
            BufferedReader br = new BufferedReader( new InputStreamReader(urlcon.getInputStream(), "UTF-8"));
            String s;
            while ((s = br.readLine()) != null){
                System.out.println(s);
            }
            
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("SimplePost");
        frame.add(new Post(args[0]), "Center");
        frame.pack();
        frame.setVisible(true);
    }

}
