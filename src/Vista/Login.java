
package Vista;

import Modelo.LoginDAO;
import Modelo.login;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;


public class Login extends javax.swing.JFrame {
    //Intanciamos de objetos clase login y LonginDao
    login lg= new login();
    LoginDAO login = new LoginDAO();
    
    public Login() {
        initComponents();
        //No se prodra maximizar la pantalla
        setResizable(false);
        //Ventana centrada
        this.setLocationRelativeTo(null);
        setTitle("Inciciar Sesión");
    }
   //Creamos metodo validar
   public void validar(){
       //almacenamos correo, y en password convertimos de char[] a una cadena string
       String correo=txtCorreo.getText();
       String pass=String.valueOf(txtPass.getPassword());
       //Validamos que no esten vacios los campos en caso dado mandamos mensaje de error
       if(!"".equals(correo) || !"".equals(pass)){
       //El resultado se almacena en la variable lg
           lg=login.log(correo, pass);
       //Si los dos resultados son diferentes de null entramos al sistema en caso contrario marcara error   
            if(lg.getCorreo() != null && lg.getPass() != null){
       //Se intancia el objeto y lo hacemos visible
                   Sistema sis=new Sistema(lg);
                   sis.setVisible(true);
       //Ocultamos login
                   dispose();
            }else{        
                JOptionPane.showMessageDialog(null, "Correo o la contraseña incorrecta");
            }
       }else{
           JOptionPane.showMessageDialog(null, "Rellené campos");
       }
   }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        btnIniciar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/lock.png"))); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 0, 204));
        jLabel4.setText("Correo electronico");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 102, -1, -1));
        jPanel2.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 131, 246, 31));

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 0, 204));
        jLabel5.setText("Password");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        txtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassActionPerformed(evt);
            }
        });
        jPanel2.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 246, 34));

        btnIniciar.setBackground(new java.awt.Color(51, 0, 204));
        btnIniciar.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btnIniciar.setForeground(new java.awt.Color(255, 255, 255));
        btnIniciar.setText("Iniciar");
        btnIniciar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204)));
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });
        jPanel2.add(btnIniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 87, -1));

        jPanel3.setBackground(new java.awt.Color(0, 0, 255));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/fb.png"))); // NOI18N
        jLabel6.setText("Siguenos en");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6)
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));

        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/github.png"))); // NOI18N
        jLabel7.setText("GitHub");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 252, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 270, 400));

        jPanel1.setBackground(new java.awt.Color(0, 255, 204));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/upiicsa_3.png"))); // NOI18N
        jLabel8.setText("jLabel8");

        jLabel9.setFont(new java.awt.Font("Tw Cen MT", 3, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Ciencias UPIICSA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 163, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(0, 23, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 440));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/log.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, 290, 440));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassActionPerformed

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        //Llamamos al metodo validar
        validar();
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // Boton fb
        if(java.awt.Desktop.isDesktopSupported()){
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if(desktop.isSupported(java.awt.Desktop.Action.BROWSE)){
                try {
                    java.net.URI url=new java.net.URI("https://www.facebook.com/brian.murillosalas.1/");
                    desktop.browse(url);
                } catch (URISyntaxException | IOException e) {
                    System.out.println(e.toString());
                }
            }
        }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        if(java.awt.Desktop.isDesktopSupported()){
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if(desktop.isSupported(java.awt.Desktop.Action.BROWSE)){
                try {
                    java.net.URI url=new java.net.URI("https://github.com/Brian-54");
                    desktop.browse(url);
                } catch (URISyntaxException | IOException e) {
                    System.out.println(e.toString());
                }
            }
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JPasswordField txtPass;
    // End of variables declaration//GEN-END:variables
}
