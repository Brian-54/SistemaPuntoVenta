
package Vista;

import Modelo.Cliente;
import Modelo.ClienteDao;
import Modelo.Config;
import Modelo.Detalle;
import Modelo.Eventos;
import Modelo.Productos;
import Modelo.ProductosDao;
import Modelo.Proveedor;
import Modelo.ProveedorDao;
import Modelo.Venta;
import Modelo.VentaDao;
import Modelo.login;
import Reportes.Excel;
import Reportes.Grafico;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.awt.Desktop;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;


public class Sistema extends javax.swing.JFrame {
    Date fechaVenta = new Date();
    String fechaActual=new SimpleDateFormat("dd/MM/yyyy").format(fechaVenta);
    //Intanciamos todas los objetos a usar
    Cliente cl=new Cliente();
    ClienteDao cliente=new ClienteDao();
    Proveedor pr= new Proveedor();
    ProveedorDao PrDao=new ProveedorDao();
    Productos pro=new Productos();
    ProductosDao proDao=new ProductosDao();
    Venta v=new Venta();
    VentaDao vDao=new VentaDao();
    Detalle Dv = new Detalle();
    Config conf=new Config();
    Eventos event = new Eventos();
    DefaultTableModel modelo=new DefaultTableModel();
    DefaultTableModel tmp = new DefaultTableModel();
    //Variables a usar
    int item;
    double Totalpagar=0.00;
    /**
     * Creates new form Sistema
     */
    public Sistema() {
        initComponents();
        
    }
    public Sistema(login priv){
        initComponents();
        setTitle("Punto de Venta");
        //Centramos frame y bloqueamos el boton de maximizar
        this.setLocationRelativeTo(null);
        setResizable(false);
        //Escondemos los id de las ventanas
        txtIdCliente.setVisible(false);
        txtIdProveedor.setVisible(false);
        txtIdPro.setVisible(false);
        txtIdVentas.setVisible(false);
        txtIdpro.setVisible(false);
        txtIdConfig.setVisible(false);
        AutoCompleteDecorator.decorate(cbxProveedorPro);
        proDao.ConsultarProveedor(cbxProveedorPro);
        if(priv.getRol().equals("Asistente")){
            btnListarProveedor.setEnabled(false);
            btnUsuarios.setEnabled(false);
            btnConfig.setEnabled(false);
            btnGuardarPro.setEnabled(false);
            btnEliminarPro.setEnabled(false);
            btnGraficar.setEnabled(false);
            Midate.setEnabled(false);
            lbllVendedor.setText(priv.getNombre());
            
        }else{
            lbllVendedor.setText(priv.getNombre());
        }
    }
    
    //Metodo listar en tabla cliente
    public void ListarCliente(){
        //Pasamos la lista de clientes a ListarCl
        List<Cliente> ListarCl=cliente.ListarCliente();
        //getmodel() Devuelve el tablemodel que proporciona los datos mostrados por este JTable
        modelo=(DefaultTableModel) TableCliente.getModel();
        //Se crea arreglo de objetos
        Object[] ob= new Object[6];
        //Recorremos las filas 
        for (int i = 0; i < ListarCl.size(); i++) {
            ob[0]= ListarCl.get(i).getId();
            ob[1]= ListarCl.get(i).getDni();
            ob[2]= ListarCl.get(i).getNombre();
            ob[3]= ListarCl.get(i).getTelefono();
            ob[4]= ListarCl.get(i).getDireccion();
            ob[5]= ListarCl.get(i).getRazon();
        //Cada iteracion agregamos objeto al modelo    
            modelo.addRow(ob);
        }
        /*setmodel()Establece el modelo de datos para la TableCliente y se registra con él para 
        recibir notificaciones de escucha del nuevo modelo de datos.*/
        TableCliente.setModel(modelo);
    } 
    //Metodo listar en tabla proveedor
    public void ListarProveedor(){
        List<Proveedor> ListarPr=PrDao.ListarProvedor();
        modelo=(DefaultTableModel) TableProveedor.getModel();
        Object[] ob= new Object[6];
        for (int i = 0; i < ListarPr.size(); i++) {
            ob[0]= ListarPr.get(i).getId();
            ob[1]= ListarPr.get(i).getRuc();
            ob[2]= ListarPr.get(i).getNombre();
            ob[3]= ListarPr.get(i).getTelefono();
            ob[4]= ListarPr.get(i).getDireccion();
            ob[5]= ListarPr.get(i).getRazon();
            modelo.addRow(ob);
        }
        TableProveedor.setModel(modelo);
    } 
    //Metodo listar en tabla productos
    public void ListarProductos(){
        List<Productos> ListarPro=proDao.ListarProducto();
        modelo=(DefaultTableModel) TableProducto.getModel();
        Object[] ob= new Object[6];
        for (int i = 0; i < ListarPro.size(); i++) {
            ob[0]= ListarPro.get(i).getId();
            ob[1]= ListarPro.get(i).getCodigo();
            ob[2]= ListarPro.get(i).getNombre();
            ob[3]= ListarPro.get(i).getProveedor();
            ob[4]= ListarPro.get(i).getStock();
            ob[5]= ListarPro.get(i).getPrecio();
            modelo.addRow(ob);
        }
        TableProducto.setModel(modelo);
    } 
    
    public void ListarConfig(){
        conf=proDao.BuscarDatos();
        txtIdConfig.setText(""+conf.getId());
        txtRucConfig.setText(""+conf.getRuc());
        txtNombreConfig.setText(""+conf.getNombre());
        txtTelefonoConfig.setText(""+conf.getTelefono());
        txtDireccionConfig.setText(""+conf.getDireccion());
        txtRazonConfig.setText(""+conf.getRazon());
    }
    
     public void ListarVentas(){
        List<Venta> ListarVenta=vDao.ListarVentas();
        modelo=(DefaultTableModel) TableVentas.getModel();
        Object[] ob= new Object[6];
        for (int i = 0; i < ListarVenta.size(); i++) {
            ob[0]= ListarVenta.get(i).getDni();
            ob[1]= ListarVenta.get(i).getCliente();
            ob[2]= ListarVenta.get(i).getVendedor();
            ob[3]= ListarVenta.get(i).getTotal();
            modelo.addRow(ob);
        }
        TableVentas.setModel(modelo);
    } 
    
    //Metodo limpiar tabla
    public void LimpiarTable(){
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i=i-1;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnNuevaVenta = new javax.swing.JButton();
        btnListaCliente = new javax.swing.JButton();
        btnListarProveedor = new javax.swing.JButton();
        btnListarProducto = new javax.swing.JButton();
        btnListarVenta = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lbllVendedor = new javax.swing.JLabel();
        btnUsuarios = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnEliminarVenta = new javax.swing.JButton();
        txtCodigoVenta = new javax.swing.JTextField();
        txtDescripcionVenta = new javax.swing.JTextField();
        txtCantidadVenta = new javax.swing.JTextField();
        txtPrecioVenta = new javax.swing.JTextField();
        txtStockDisponible = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableVenta = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtRucVenta = new javax.swing.JTextField();
        txtNombreClienteVenta = new javax.swing.JTextField();
        btnGenerarVenta = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        LabelTotal = new javax.swing.JLabel();
        txtTelefonoCV = new javax.swing.JTextField();
        txtDireccionCV = new javax.swing.JTextField();
        txtRazonCV = new javax.swing.JTextField();
        txtIdpro = new javax.swing.JTextField();
        btnGraficar = new javax.swing.JButton();
        Midate = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtDniCliente = new javax.swing.JTextField();
        txtNombreCliente = new javax.swing.JTextField();
        txtTelefonoCliente = new javax.swing.JTextField();
        txtDireccionCliente = new javax.swing.JTextField();
        txtRazonCliente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableCliente = new javax.swing.JTable();
        btnGuardarCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        btnNuevoCliente = new javax.swing.JButton();
        txtIdCliente = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtRucProveedor = new javax.swing.JTextField();
        txtNombreProveedor = new javax.swing.JTextField();
        txtTelefonoProveedor = new javax.swing.JTextField();
        txtDireccionProveedor = new javax.swing.JTextField();
        txtRazonProveedor = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableProveedor = new javax.swing.JTable();
        btnGuardarProveedor = new javax.swing.JButton();
        btnEditarProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        btnNuevoProveedor = new javax.swing.JButton();
        txtIdProveedor = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtCodigoPro = new javax.swing.JTextField();
        txtDescripcionPro = new javax.swing.JTextField();
        txtCantidadPro = new javax.swing.JTextField();
        txtPrecioPro = new javax.swing.JTextField();
        cbxProveedorPro = new javax.swing.JComboBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableProducto = new javax.swing.JTable();
        btnGuardarPro = new javax.swing.JButton();
        btnEditarPro = new javax.swing.JButton();
        btnEliminarPro = new javax.swing.JButton();
        btnNuevoPro = new javax.swing.JButton();
        btnExcelPro = new javax.swing.JButton();
        txtIdPro = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableVentas = new javax.swing.JTable();
        btnPdfVentas = new javax.swing.JButton();
        txtIdVentas = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtRucConfig = new javax.swing.JTextField();
        txtNombreConfig = new javax.swing.JTextField();
        txtTelefonoConfig = new javax.swing.JTextField();
        txtDireccionConfig = new javax.swing.JTextField();
        txtRazonConfig = new javax.swing.JTextField();
        btnActualizarConfig = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        txtIdConfig = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(102, 0, 153));

        btnNuevaVenta.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Nventa.png"))); // NOI18N
        btnNuevaVenta.setText("Nueva Venta");
        btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaVentaActionPerformed(evt);
            }
        });

        btnListaCliente.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnListaCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Clientes.png"))); // NOI18N
        btnListaCliente.setText("Clientes");
        btnListaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListaClienteActionPerformed(evt);
            }
        });

        btnListarProveedor.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnListarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/proveedor.png"))); // NOI18N
        btnListarProveedor.setText("Proveedor");
        btnListarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarProveedorActionPerformed(evt);
            }
        });

        btnListarProducto.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnListarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/producto.png"))); // NOI18N
        btnListarProducto.setText("Productos");
        btnListarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarProductoActionPerformed(evt);
            }
        });

        btnListarVenta.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnListarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/compras.png"))); // NOI18N
        btnListarVenta.setText("Ventas");
        btnListarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarVentaActionPerformed(evt);
            }
        });

        btnConfig.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/config.png"))); // NOI18N
        btnConfig.setText("Config");
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/upiicsa_3.png"))); // NOI18N

        lbllVendedor.setText("Murillo 54");

        btnUsuarios.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Clientes.png"))); // NOI18N
        btnUsuarios.setText("Usuarios");
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnNuevaVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnListaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnListarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnListarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnListarVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnConfig, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbllVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
            .addComponent(btnUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbllVendedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnListaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnListarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(btnListarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnListarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 600));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/PVenta1.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 830, 180));

        jLabel3.setText("Código");

        jLabel4.setText("Descripción");

        jLabel5.setText("Cantidad");

        jLabel6.setText("Precio");

        jLabel7.setText("Stock Disponible");

        btnEliminarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btnEliminarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarVentaActionPerformed(evt);
            }
        });

        txtCodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyTyped(evt);
            }
        });

        txtDescripcionVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionVentaKeyTyped(evt);
            }
        });

        txtCantidadVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyTyped(evt);
            }
        });

        txtPrecioVenta.setEditable(false);

        txtStockDisponible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockDisponibleActionPerformed(evt);
            }
        });

        TableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CÓDIGO", "DESCRIPCIÓN", "CANTIDAD", "PRECIO", "TOTAL"
            }
        ));
        jScrollPane1.setViewportView(TableVenta);
        if (TableVenta.getColumnModel().getColumnCount() > 0) {
            TableVenta.getColumnModel().getColumn(0).setPreferredWidth(30);
            TableVenta.getColumnModel().getColumn(1).setPreferredWidth(100);
            TableVenta.getColumnModel().getColumn(2).setPreferredWidth(30);
            TableVenta.getColumnModel().getColumn(3).setPreferredWidth(30);
            TableVenta.getColumnModel().getColumn(4).setPreferredWidth(40);
        }

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("DNI/RUC:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("NOMBRE:");

        txtRucVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRucVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucVentaKeyTyped(evt);
            }
        });

        txtNombreClienteVenta.setEditable(false);

        btnGenerarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/print.png"))); // NOI18N
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/money.png"))); // NOI18N
        jLabel10.setText("TOTAL A PAGAR:");

        LabelTotal.setText("-----");

        btnGraficar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/torta.png"))); // NOI18N
        btnGraficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficarActionPerformed(evt);
            }
        });

        jLabel11.setText("Seleccionar:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel3)
                                .addGap(50, 50, 50)
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDescripcionVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCantidadVenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel6)))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtStockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtIdpro, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarVenta))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGraficar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Midate, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRucVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNombreClienteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtTelefonoCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDireccionCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtRazonCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(110, 110, 110)
                        .addComponent(btnGenerarVenta)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(91, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnGraficar)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Midate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdpro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnEliminarVenta))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnGenerarVenta)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(LabelTotal)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreClienteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txtRucVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTelefonoCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDireccionCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRazonCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        jTabbedPane1.addTab("tab1", jPanel2);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("DNI/ruc:");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Nombre:");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Telefono:");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Dirección:");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Razón Social:");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, -1, -1));

        txtDniCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniClienteKeyTyped(evt);
            }
        });
        jPanel3.add(txtDniCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 83, -1));

        txtNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreClienteKeyTyped(evt);
            }
        });
        jPanel3.add(txtNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 155, -1));

        txtTelefonoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoClienteKeyTyped(evt);
            }
        });
        jPanel3.add(txtTelefonoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 155, -1));

        txtDireccionCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionClienteKeyTyped(evt);
            }
        });
        jPanel3.add(txtDireccionCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 155, -1));

        txtRazonCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRazonClienteKeyTyped(evt);
            }
        });
        jPanel3.add(txtRazonCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 155, -1));

        TableCliente.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        TableCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DNI/RUC", "NOMBRE", "TELEFONO", "DIRECCIÓN", "RAZON SOCIAL"
            }
        ));
        TableCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TableCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableCliente);
        if (TableCliente.getColumnModel().getColumnCount() > 0) {
            TableCliente.getColumnModel().getColumn(0).setPreferredWidth(30);
            TableCliente.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableCliente.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableCliente.getColumnModel().getColumn(4).setPreferredWidth(80);
            TableCliente.getColumnModel().getColumn(5).setPreferredWidth(80);
        }

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 560, 300));

        btnGuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/GuardarTodo.png"))); // NOI18N
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });
        jPanel3.add(btnGuardarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, 47, 38));

        btnEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Actualizar (2).png"))); // NOI18N
        btnEditarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });
        jPanel3.add(btnEditarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 340, 48, 38));

        btnEliminarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btnEliminarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });
        jPanel3.add(btnEliminarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, 47, 37));

        btnNuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });
        jPanel3.add(btnNuevoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 390, 48, 37));
        jPanel3.add(txtIdCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 17, -1));

        jTabbedPane1.addTab("tab2", jPanel3);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("DNI/RUC:");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Nombre:");
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Telefono: ");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Dirección:");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Razón Social:");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        txtRucProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucProveedorKeyTyped(evt);
            }
        });
        jPanel4.add(txtRucProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 93, -1));

        txtNombreProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProveedorKeyTyped(evt);
            }
        });
        jPanel4.add(txtNombreProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 154, -1));

        txtTelefonoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoProveedorActionPerformed(evt);
            }
        });
        txtTelefonoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoProveedorKeyTyped(evt);
            }
        });
        jPanel4.add(txtTelefonoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 154, -1));

        txtDireccionProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionProveedorKeyTyped(evt);
            }
        });
        jPanel4.add(txtDireccionProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, 154, -1));

        txtRazonProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRazonProveedorKeyTyped(evt);
            }
        });
        jPanel4.add(txtRazonProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, 155, -1));

        TableProveedor.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        TableProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RUC", "NOMBRE", "TELEFONO", "DIRECCIÓN", "RAZON SOCIAL"
            }
        ));
        TableProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableProveedor);
        if (TableProveedor.getColumnModel().getColumnCount() > 0) {
            TableProveedor.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableProveedor.getColumnModel().getColumn(1).setPreferredWidth(40);
            TableProveedor.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableProveedor.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableProveedor.getColumnModel().getColumn(4).setPreferredWidth(80);
            TableProveedor.getColumnModel().getColumn(5).setPreferredWidth(70);
        }

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, 540, 320));

        btnGuardarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/GuardarTodo.png"))); // NOI18N
        btnGuardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProveedorActionPerformed(evt);
            }
        });
        jPanel4.add(btnGuardarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, 50, 40));

        btnEditarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Actualizar (2).png"))); // NOI18N
        btnEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProveedorActionPerformed(evt);
            }
        });
        jPanel4.add(btnEditarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, 50, 40));

        btnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });
        jPanel4.add(btnEliminarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 380, 50, 40));

        btnNuevoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProveedorActionPerformed(evt);
            }
        });
        jPanel4.add(btnNuevoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 380, 50, 40));
        jPanel4.add(txtIdProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 25, -1));

        jTabbedPane1.addTab("tab3", jPanel4);

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("Código:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Descripción:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Cantidad:");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Precio:");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Proveedor:");

        txtCodigoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoProActionPerformed(evt);
            }
        });
        txtCodigoPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoProKeyTyped(evt);
            }
        });

        txtDescripcionPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionProActionPerformed(evt);
            }
        });
        txtDescripcionPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionProKeyTyped(evt);
            }
        });

        txtCantidadPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadProActionPerformed(evt);
            }
        });
        txtCantidadPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadProKeyTyped(evt);
            }
        });

        txtPrecioPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProKeyTyped(evt);
            }
        });

        cbxProveedorPro.setEditable(true);
        cbxProveedorPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cbxProveedorProKeyTyped(evt);
            }
        });

        TableProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CÓDIGO ", "DESCRIPCIÓN", "PROVEEDOR", "STOCK", "PRECIO"
            }
        ));
        TableProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProductoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableProducto);
        if (TableProducto.getColumnModel().getColumnCount() > 0) {
            TableProducto.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableProducto.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableProducto.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableProducto.getColumnModel().getColumn(3).setPreferredWidth(60);
            TableProducto.getColumnModel().getColumn(4).setPreferredWidth(40);
            TableProducto.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        btnGuardarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/GuardarTodo.png"))); // NOI18N
        btnGuardarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProActionPerformed(evt);
            }
        });

        btnEditarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Actualizar (2).png"))); // NOI18N
        btnEditarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProActionPerformed(evt);
            }
        });

        btnEliminarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btnEliminarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProActionPerformed(evt);
            }
        });

        btnNuevoPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProActionPerformed(evt);
            }
        });

        btnExcelPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/excel.png"))); // NOI18N
        btnExcelPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelProActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel25))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtDescripcionPro, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtCantidadPro, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                            .addComponent(txtPrecioPro)))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(17, 17, 17)
                                        .addComponent(txtIdPro))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel26)
                                    .addComponent(btnGuardarPro, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(btnEditarPro, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnEliminarPro, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(cbxProveedorPro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNuevoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnExcelPro, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)))
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(121, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel22)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtIdPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txtDescripcionPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(txtCantidadPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(txtPrecioPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(cbxProveedorPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGuardarPro, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                            .addComponent(btnEditarPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminarPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNuevoPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnExcelPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(20, 20, 20))
        );

        jTabbedPane1.addTab("tab4", jPanel5);

        TableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "VENDEDOR", "TOTAL"
            }
        ));
        TableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableVentasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableVentas);
        if (TableVentas.getColumnModel().getColumnCount() > 0) {
            TableVentas.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(1).setPreferredWidth(60);
            TableVentas.getColumnModel().getColumn(2).setPreferredWidth(60);
            TableVentas.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        btnPdfVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/pdf.png"))); // NOI18N
        btnPdfVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfVentasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPdfVentas)
                        .addGap(44, 44, 44)
                        .addComponent(txtIdVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(122, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIdVentas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPdfVentas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jTabbedPane1.addTab("tab5", jPanel6);

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("RUC");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("NOMBRE DE LA EMPRESA");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("TELEFONO");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("DIRECCIÓN");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("RAZON SOCIAL");

        txtRucConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucConfigKeyTyped(evt);
            }
        });

        txtNombreConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreConfigKeyTyped(evt);
            }
        });

        txtTelefonoConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoConfigKeyTyped(evt);
            }
        });

        txtRazonConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRazonConfigKeyTyped(evt);
            }
        });

        btnActualizarConfig.setFont(new java.awt.Font("Tw Cen MT", 2, 14)); // NOI18N
        btnActualizarConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Actualizar (2).png"))); // NOI18N
        btnActualizarConfig.setText("ACTUALIZAR");
        btnActualizarConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarConfigActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel32.setText("DATOS DE LA EMPRESA");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnActualizarConfig)
                .addGap(336, 336, 336))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtDireccionConfig)
                                .addComponent(txtRucConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(jLabel30))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(jLabel27))
                            .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGap(69, 69, 69)
                                        .addComponent(txtNombreConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGap(99, 99, 99)
                                        .addComponent(jLabel28))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtRazonConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(74, 74, 74)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTelefonoConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel29)
                                        .addGap(57, 57, 57))))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(126, 126, 126)
                                .addComponent(jLabel31))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(269, 269, 269)
                        .addComponent(jLabel32)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(120, Short.MAX_VALUE)
                .addComponent(jLabel32)
                .addGap(18, 18, 18)
                .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addGap(27, 27, 27)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefonoConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRucConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccionConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRazonConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addComponent(btnActualizarConfig)
                .addGap(47, 47, 47))
        );

        jTabbedPane1.addTab("tab6", jPanel7);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 830, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtStockDisponibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockDisponibleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockDisponibleActionPerformed

    private void txtTelefonoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoProveedorActionPerformed

    private void txtCodigoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoProActionPerformed

    private void txtDescripcionProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionProActionPerformed

    private void txtCantidadProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadProActionPerformed

    private void btnGuardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProveedorActionPerformed
           if(!"".equals(txtRucProveedor.getText())||!"".equals(txtNombreProveedor.getText())||!"".equals(txtTelefonoProveedor.getText())||!"".equals(txtDireccionProveedor.getText())||!"".equals(txtRazonProveedor.getText())){
               pr.setRuc(Integer.parseInt(txtRucProveedor.getText()));
               pr.setNombre(txtNombreProveedor.getText());
               pr.setTelefono(Integer.parseInt(txtTelefonoProveedor.getText()));
               pr.setDireccion(txtDireccionProveedor.getText());
               pr.setRazon(txtRazonProveedor.getText());
               PrDao.RegistrarProveedor(pr);
               JOptionPane.showMessageDialog(null,"Proveedor Registrado");
               LimpiarTable();
               ListarProveedor();
               LimpiarProveedor();
           }else{
               JOptionPane.showMessageDialog(null, "Los campos estan vacios");
           }    
    }//GEN-LAST:event_btnGuardarProveedorActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
      //Verificamos que los campos no esten vacios
        if(!"".equals(txtDniCliente.getText()) || !"".equals(txtNombreCliente.getText()) || !"".equals(txtTelefonoCliente.getText()) || !"".equals(txtDireccionCliente.getText())){
          //Guardamos datos en el objeto cl
           cl.setDni(Integer.parseInt(txtDniCliente.getText()));
           cl.setNombre(txtNombreCliente.getText());
           cl.setTelefono(Integer.parseInt(txtTelefonoCliente.getText()));
           cl.setDireccion(txtDireccionCliente.getText());
           cl.setRazon(txtRazonCliente.getText());
           //Enviamos objeto mediante metodo
           cliente.RegistrarCliente(cl);
           JOptionPane.showMessageDialog(null,"Cliente Registrado");
           LimpiarTable();
           LimpiarCliente();
           ListarCliente();
        }else{
           JOptionPane.showMessageDialog(null,"Campos vacios");
        }
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void btnListaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaClienteActionPerformed
        LimpiarTable();
        ListarCliente();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_btnListaClienteActionPerformed

    private void TableClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableClienteMouseClicked
        int fila=TableCliente.rowAtPoint(evt.getPoint());
        txtIdCliente.setText(TableCliente.getValueAt(fila, 0).toString());
        txtDniCliente.setText(TableCliente.getValueAt(fila, 1).toString());
        txtNombreCliente.setText(TableCliente.getValueAt(fila, 2).toString());
        txtTelefonoCliente.setText(TableCliente.getValueAt(fila, 3).toString());
        txtDireccionCliente.setText(TableCliente.getValueAt(fila, 4).toString());
        txtRazonCliente.setText(TableCliente.getValueAt(fila, 5).toString());
        
    }//GEN-LAST:event_TableClienteMouseClicked

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        if(!"".equals(txtIdCliente.getText())){
            int pregunta=JOptionPane.showConfirmDialog(null,"Esta seguro de eliminar");
            if (pregunta==0) {
                int id=Integer.parseInt(txtIdCliente.getText());
                cliente.EliminarCliente(id);
                LimpiarTable();
                LimpiarCliente();
                ListarCliente();
            } else {
                JOptionPane.showMessageDialog(null, "Campos vacios");
            }
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        if("".equals(txtIdCliente.getText())){
            JOptionPane.showMessageDialog(null,"Seleccione una fila");
        }else{ 
            if (!"".equals(txtDniCliente.getText()) || !"".equals(txtNombreCliente.getText()) || !"".equals(txtTelefonoCliente.getText()) || !"".equals(txtDireccionCliente.getText())) {
                cl.setDni(Integer.parseInt(txtDniCliente.getText()));
                cl.setNombre(txtNombreCliente.getText());
                cl.setTelefono(Integer.parseInt(txtTelefonoCliente.getText()));
                cl.setDireccion(txtDireccionCliente.getText());
                cl.setRazon(txtRazonCliente.getText());
                cl.setInt(Integer.parseInt(txtIdCliente.getText()));
                cliente.ModificarCliente(cl);
                JOptionPane.showMessageDialog(null,"Cliente Modificado");
                LimpiarTable();
                LimpiarCliente();
                ListarCliente();
            }else{
                JOptionPane.showMessageDialog(null,"Campos Vacios");
            }
        }
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
           LimpiarCliente();
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void btnListarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarProveedorActionPerformed
       LimpiarTable();
       ListarProveedor();
       jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_btnListarProveedorActionPerformed

    private void TableProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProveedorMouseClicked
        int fila= TableProveedor.rowAtPoint(evt.getPoint());
        txtIdProveedor.setText(TableProveedor.getValueAt(fila, 0).toString());
        txtRucProveedor.setText(TableProveedor.getValueAt(fila, 1).toString());
        txtNombreProveedor.setText(TableProveedor.getValueAt(fila, 2).toString());
        txtTelefonoProveedor.setText(TableProveedor.getValueAt(fila, 3).toString());
        txtDireccionProveedor.setText(TableProveedor.getValueAt(fila, 4).toString());
        txtRazonProveedor.setText(TableProveedor.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_TableProveedorMouseClicked

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        if(!"".equals(txtIdProveedor.getText())){
            int pregunta=JOptionPane.showConfirmDialog(null, "Estas seguro de eliminar");
            if(pregunta==0){
                int id=Integer.parseInt(txtIdProveedor.getText());
                PrDao.EliminarProveedor(id);
                LimpiarTable();
                ListarProveedor();
                LimpiarProveedor();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void btnEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProveedorActionPerformed
        if("".equals(txtIdProveedor.getText())){
            JOptionPane.showMessageDialog(null,"Seleccion una fila");
        }else{
            if(!"".equals(txtRucProveedor.getText())||!"".equals(txtNombreProveedor.getText())||!"".equals(txtTelefonoProveedor.getText())||!"".equals(txtDireccionProveedor.getText())||!"".equals(txtRazonProveedor.getText())){
                pr.setRuc(Integer.parseInt(txtRucProveedor.getText()));
                pr.setNombre(txtNombreProveedor.getText());
                pr.setTelefono(Integer.parseInt(txtTelefonoProveedor.getText()));
                pr.setDireccion(txtDireccionProveedor.getText());
                pr.setRazon(txtRazonProveedor.getText());
                pr.setId(Integer.parseInt(txtIdProveedor.getText()));
                PrDao.ModificarProveedor(pr);
                JOptionPane.showMessageDialog(null,"Proveedor Modificado");
                LimpiarTable();
                ListarProveedor();
                LimpiarProveedor(); 
            }else{
                JOptionPane.showMessageDialog(null,"Campos Vacios");
            }
        }
    }//GEN-LAST:event_btnEditarProveedorActionPerformed

    private void btnNuevoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProveedorActionPerformed
        LimpiarProveedor();
    }//GEN-LAST:event_btnNuevoProveedorActionPerformed

    private void btnGuardarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProActionPerformed
        if (!"".equals(txtCodigoPro.getText()) || !"".equals(txtDescripcionPro.getText()) || !"".equals(cbxProveedorPro.getSelectedItem()) || !"".equals(txtCantidadPro.getText()) || !"".equals(txtPrecioPro.getText())) {
            pro.setCodigo(txtCodigoPro.getText());
            pro.setNombre(txtDescripcionPro.getText());
            pro.setProveedor(cbxProveedorPro.getSelectedItem().toString());
            pro.setStock(Integer.parseInt(txtCantidadPro.getText()));
            pro.setPrecio(Double.parseDouble(txtPrecioPro.getText()));
            proDao.RegistrarProductos(pro);
            JOptionPane.showMessageDialog(null,"Producto Registrado");
           LimpiarTable();
           LimpiarProducto();
           ListarProductos();
        }else{
            JOptionPane.showMessageDialog(null,"Campos estan vacios");
        }

    }//GEN-LAST:event_btnGuardarProActionPerformed

    private void btnListarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarProductoActionPerformed
        LimpiarTable();
        ListarProductos();
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_btnListarProductoActionPerformed

    private void TableProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProductoMouseClicked
        int fila= TableProducto.rowAtPoint(evt.getPoint());
        txtIdPro.setText(TableProducto.getValueAt(fila, 0).toString());
        txtCodigoPro.setText(TableProducto.getValueAt(fila, 1).toString());
        txtDescripcionPro.setText(TableProducto.getValueAt(fila, 2).toString());
        cbxProveedorPro.setSelectedItem(TableProducto.getValueAt(fila, 3).toString());
        txtCantidadPro.setText(TableProducto.getValueAt(fila, 4).toString());
        txtPrecioPro.setText(TableProducto.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_TableProductoMouseClicked

    private void btnEliminarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProActionPerformed
         if(!"".equals(txtIdPro.getText())){
            int pregunta=JOptionPane.showConfirmDialog(null,"Esta seguro de eliminar");
            if (pregunta==0) {
                int id=Integer.parseInt(txtIdPro.getText());
                proDao.EliminarProducto(id);
                LimpiarTable();
                LimpiarProducto();
                ListarProductos();
            } else {
                JOptionPane.showMessageDialog(null, "Campos vacios");
            }
        }
    }//GEN-LAST:event_btnEliminarProActionPerformed

    private void btnNuevoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProActionPerformed
        LimpiarProducto();
    }//GEN-LAST:event_btnNuevoProActionPerformed

    private void btnEditarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProActionPerformed
       if("".equals(txtIdPro.getText())){
            JOptionPane.showMessageDialog(null,"Seleccion una fila");
        }else{
            if(!"".equals(txtCodigoPro.getText())||!"".equals(txtDescripcionPro.getText())||!"".equals(txtCantidadPro.getText())||!"".equals(txtPrecioPro.getText())){
                pro.setCodigo(txtCodigoPro.getText());
                pro.setNombre(txtDescripcionPro.getText());
                pro.setProveedor(cbxProveedorPro.getSelectedItem().toString());
                pro.setStock(Integer.parseInt(txtCantidadPro.getText()));
                pro.setPrecio(Double.parseDouble(txtPrecioPro.getText()));
                pro.setId(Integer.parseInt(txtIdPro.getText()));
                proDao.ModificarProductos(pro);
                JOptionPane.showMessageDialog(null,"Producto Modificado");
                LimpiarTable();
                ListarProductos();
                LimpiarProducto(); 
            }else{
                JOptionPane.showMessageDialog(null,"Campos Vacios");
            }
        }
    }//GEN-LAST:event_btnEditarProActionPerformed

    private void btnExcelProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelProActionPerformed
        Excel.reporte();
    }//GEN-LAST:event_btnExcelProActionPerformed

    private void txtCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(!"".equals(txtCodigoVenta.getText())){
                String cod=txtCodigoVenta.getText();
                pro=proDao.BuscarPro(cod);
               if(pro.getNombre() != null){
                   txtDescripcionVenta.setText(""+pro.getNombre());
                   txtPrecioVenta.setText(""+pro.getPrecio());
                   txtStockDisponible.setText(""+pro.getStock());
                   txtCantidadVenta.requestFocus();
               }else{
                   LimpiarVenta();
                   txtCodigoVenta.requestFocus();
               }
            }else{
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del producto");
                txtCodigoVenta.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoVentaKeyPressed
//Validacion de no superacion de pedido a stoch
    private void txtCantidadVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
             if(!"".equals(txtCantidadVenta.getText())){
                 String cod=txtCodigoVenta.getText();
                 String descripcion=txtDescripcionVenta.getText();
                 int cant=Integer.parseInt(txtCantidadVenta.getText());
                 double precio=Double.parseDouble(txtPrecioVenta.getText());
                 double total= cant*precio;
                 int stock=Integer.parseInt(txtStockDisponible.getText());
                 if(stock >= cant){
                     item=item+1;
                     DefaultTableModel tmp=(DefaultTableModel) TableVenta.getModel();
                     for (int i = 0 ; i< TableVenta.getRowCount() ; i++){
                         if(TableVenta.getValueAt(i, 1).equals(txtDescripcionVenta.getText())){
                             JOptionPane.showMessageDialog(null,"El producto ya esta registrado");
                             return;
                        }
                     }
                     ArrayList lista= new ArrayList();
                     lista.add(item);
                     lista.add(cod);
                     lista.add(descripcion);
                     lista.add(cant);
                     lista.add(precio);
                     lista.add(total);
                     Object[] O=new Object[5];
                     O[0] = lista.get(1);
                     O[1] = lista.get(2);
                     O[2] = lista.get(3);
                     O[3] = lista.get(4);
                     O[4] = lista.get(5); 
                     tmp.addRow(O);
                     TableVenta.setModel(tmp);
                     TotalPagar();
                     LimpiarVenta();
                     txtCodigoVenta.requestFocus();
                 }else{
                     JOptionPane.showMessageDialog(null,"Stock no disponible");
                 }
             }else{
                  JOptionPane.showMessageDialog(null,"Ingrese Cantidad");
             }
        }
    }//GEN-LAST:event_txtCantidadVentaKeyPressed

    private void btnEliminarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarVentaActionPerformed
        modelo = (DefaultTableModel) TableVenta.getModel();
        modelo.removeRow(TableVenta.getSelectedRow());
        TotalPagar();
        txtCodigoVenta.requestFocus();
    }//GEN-LAST:event_btnEliminarVentaActionPerformed

    private void txtRucVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucVentaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            if(!"".equals(txtRucVenta.getText())){
                int dni = Integer.parseInt(txtRucVenta.getText());
                cl = cliente.BuscarCliente(dni);
                if(cl.getNombre() !=null){
                    txtNombreClienteVenta.setText(""+cl.getNombre());
                    txtTelefonoCV.setText(""+cl.getTelefono());
                    txtDireccionCV.setText(""+cl.getDireccion());
                    txtRazonCV.setText(""+cl.getRazon());
                }else{
                    txtRucVenta.setText("");
                    JOptionPane.showMessageDialog(null,"El cliente no existe");
                }
            }
        }
    }//GEN-LAST:event_txtRucVentaKeyPressed

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed
        if (TableVenta.getRowCount() > 0) {
            ListarConfig();
            if (!"".equals(txtNombreClienteVenta.getText())) {
                RegistarVenta();
                RegistrarDetalle();
                ActualizarStock();
                pdf();
                LimpiarTableVenta();
                LimpiarClienteVenta();
            }else{
                JOptionPane.showMessageDialog(null,"Debes buscar un cliente");
            }
        }else {
                JOptionPane.showMessageDialog(null,"No hay productos enla venta");
        }
    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void btnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaVentaActionPerformed
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_btnNuevaVentaActionPerformed

    private void txtCodigoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoVentaKeyTyped

    private void txtDescripcionVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionVentaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionVentaKeyTyped

    private void txtCantidadVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadVentaKeyTyped

    private void txtRucVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucVentaKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtRucVentaKeyTyped

    private void txtDniClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniClienteKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtDniClienteKeyTyped

    private void txtNombreClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteKeyTyped
        // TODO add your handling code here:
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreClienteKeyTyped

    private void txtTelefonoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoClienteKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtTelefonoClienteKeyTyped

    private void txtDireccionClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionClienteKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionClienteKeyTyped

    private void txtRazonClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonClienteKeyTyped
        // TODO add your handling code here:
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtRazonClienteKeyTyped

    private void txtRucProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucProveedorKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtRucProveedorKeyTyped

    private void txtNombreProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProveedorKeyTyped
        // TODO add your handling code here:
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreProveedorKeyTyped

    private void txtTelefonoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoProveedorKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtTelefonoProveedorKeyTyped

    private void txtDireccionProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionProveedorKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionProveedorKeyTyped

    private void txtRazonProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonProveedorKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazonProveedorKeyTyped

    private void txtCodigoProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoProKeyTyped

    private void txtDescripcionProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionProKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionProKeyTyped

    private void txtCantidadProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadProKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadProKeyTyped

    private void txtPrecioProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProKeyTyped
        // TODO add your handling code here:
        event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtPrecioProKeyTyped

    private void cbxProveedorProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbxProveedorProKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxProveedorProKeyTyped

    private void txtRucConfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucConfigKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtRucConfigKeyTyped

    private void txtNombreConfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreConfigKeyTyped
        // TODO add your handling code here:
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreConfigKeyTyped

    private void txtTelefonoConfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoConfigKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtTelefonoConfigKeyTyped

    private void txtRazonConfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonConfigKeyTyped
        // TODO add your handling code here:
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtRazonConfigKeyTyped

    private void btnActualizarConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarConfigActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtRucConfig.getText()) || !"".equals(txtNombreConfig.getText()) || !"".equals(txtTelefonoConfig.getText()) || !"".equals(txtDireccionConfig.getText())) {
                conf.setRuc(Integer.parseInt(txtRucConfig.getText()));
                conf.setNombre(txtNombreConfig.getText());
                conf.setTelefono(Integer.parseInt(txtTelefonoConfig.getText()));
                conf.setDireccion(txtDireccionConfig.getText());
                conf.setRazon(txtRazonConfig.getText());
                conf.setId(Integer.parseInt(txtIdConfig.getText()));
                proDao.ModificarDatos(conf);
                JOptionPane.showMessageDialog(null,"Datos de la empresa modificado");
                ListarConfig();
            }else{
                JOptionPane.showMessageDialog(null,"Campos Vacios");
            }
    }//GEN-LAST:event_btnActualizarConfigActionPerformed

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(5);
        ListarConfig();
    }//GEN-LAST:event_btnConfigActionPerformed

    private void btnListarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarVentaActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(4);
        LimpiarTableVenta();
        ListarVentas();
    }//GEN-LAST:event_btnListarVentaActionPerformed

    private void TableVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseClicked
        // TODO add your handling code here:
        int fila = TableVentas.rowAtPoint(evt.getPoint());
        txtIdVentas.setText(TableVentas.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_TableVentasMouseClicked

    private void btnPdfVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfVentasActionPerformed
        // Pdf Ventas
        try {
            int id = Integer.parseInt(txtIdVentas.getText());
            File file = new File("src/pdf/venta"+id+".pdf");
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPdfVentasActionPerformed

    private void btnGraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficarActionPerformed
        // grafico
        String fechaReporte = new SimpleDateFormat("dd/MM/yyyy").format(Midate.getDate());
        Grafico.Graficar(fechaReporte);
    }//GEN-LAST:event_btnGraficarActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        // Registro usuario
        Registro reg = new Registro();
        reg.setVisible(true);
    }//GEN-LAST:event_btnUsuariosActionPerformed

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
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });     
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelTotal;
    private com.toedter.calendar.JDateChooser Midate;
    private javax.swing.JTable TableCliente;
    private javax.swing.JTable TableProducto;
    private javax.swing.JTable TableProveedor;
    private javax.swing.JTable TableVenta;
    private javax.swing.JTable TableVentas;
    private javax.swing.JButton btnActualizarConfig;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarPro;
    private javax.swing.JButton btnEditarProveedor;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarPro;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnEliminarVenta;
    private javax.swing.JButton btnExcelPro;
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnGraficar;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarPro;
    private javax.swing.JButton btnGuardarProveedor;
    private javax.swing.JButton btnListaCliente;
    private javax.swing.JButton btnListarProducto;
    private javax.swing.JButton btnListarProveedor;
    private javax.swing.JButton btnListarVenta;
    private javax.swing.JButton btnNuevaVenta;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoPro;
    private javax.swing.JButton btnNuevoProveedor;
    private javax.swing.JButton btnPdfVentas;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JComboBox cbxProveedorPro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbllVendedor;
    private javax.swing.JTextField txtCantidadPro;
    private javax.swing.JTextField txtCantidadVenta;
    private javax.swing.JTextField txtCodigoPro;
    private javax.swing.JTextField txtCodigoVenta;
    private javax.swing.JTextField txtDescripcionPro;
    private javax.swing.JTextField txtDescripcionVenta;
    private javax.swing.JTextField txtDireccionCV;
    private javax.swing.JTextField txtDireccionCliente;
    private javax.swing.JTextField txtDireccionConfig;
    private javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtDniCliente;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdConfig;
    private javax.swing.JTextField txtIdPro;
    private javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtIdVentas;
    private javax.swing.JTextField txtIdpro;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreClienteVenta;
    private javax.swing.JTextField txtNombreConfig;
    private javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtPrecioPro;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtRazonCV;
    private javax.swing.JTextField txtRazonCliente;
    private javax.swing.JTextField txtRazonConfig;
    private javax.swing.JTextField txtRazonProveedor;
    private javax.swing.JTextField txtRucConfig;
    private javax.swing.JTextField txtRucProveedor;
    private javax.swing.JTextField txtRucVenta;
    private javax.swing.JTextField txtStockDisponible;
    private javax.swing.JTextField txtTelefonoCV;
    private javax.swing.JTextField txtTelefonoCliente;
    private javax.swing.JTextField txtTelefonoConfig;
    private javax.swing.JTextField txtTelefonoProveedor;
    // End of variables declaration//GEN-END:variables
    private void LimpiarCliente(){
        txtIdCliente.setText("");
        txtDniCliente.setText("");
        txtNombreCliente.setText("");
        txtTelefonoCliente.setText("");
        txtDireccionCliente.setText("");
        txtRazonCliente.setText("");
    }
     private void LimpiarProveedor(){
        txtIdProveedor.setText("");
        txtRucProveedor.setText("");
        txtNombreProveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtDireccionProveedor.setText("");
        txtRazonProveedor.setText("");
    }
    private void LimpiarProducto(){
        txtIdpro.setText("");
        txtCodigoPro.setText("");
        txtDescripcionPro.setText("");
        txtCantidadPro.setText("");
        txtPrecioPro.setText("");
        cbxProveedorPro.setSelectedItem(null);
    }
    private void TotalPagar(){
        Totalpagar=0.00;
        int numFila = TableVenta.getRowCount();
        for(int i=0;i<numFila;i++){
            double cal= Double.parseDouble(String.valueOf(TableVenta.getModel().getValueAt(i, 4)));
            Totalpagar = Totalpagar +cal;
        }
        LabelTotal.setText(String.format("%.2f", Totalpagar));
    }
    private void LimpiarVenta(){
        txtCodigoVenta.setText("");
        txtDescripcionVenta.setText("");
        txtCantidadVenta.setText("");
        txtStockDisponible.setText("");
        txtPrecioVenta.setText("");
        txtIdVentas.setText("");
    }
    private void RegistarVenta(){
        String client=txtNombreClienteVenta.getText();
        String vendedor= lbllVendedor.getText();
        double monto = Totalpagar;
        v.setCliente(client);
        v.setVendedor(vendedor);
        v.setTotal(monto);
        v.setFecha(fechaActual);
        vDao.RegistrarVenta(v);
    }
    private void RegistrarDetalle(){
        int id = vDao.IdVenta();
        for (int i = 0; i < TableVenta.getRowCount(); i++) {
            String cod= TableVenta.getValueAt(i, 0).toString();
            int cant=Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(TableVenta.getValueAt(i, 3).toString());
            Dv.setCod_pro(cod);
            Dv.setCantidad(cant);
            Dv.setPrecio(precio);
            Dv.setId(id);
            vDao.RegistrarDetalle(Dv);
        }
    }
    private void ActualizarStock(){
        for (int i = 0; i < TableVenta.getRowCount(); i++) {
            String cod=TableVenta.getValueAt(i, 0).toString();
            int cant=Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
            pro = proDao.BuscarPro(cod);
            int StockActual= pro.getStock()-cant;
            vDao.ActualizarStock(StockActual, cod);
        }
    }
    private void LimpiarTableVenta(){
        tmp = (DefaultTableModel) TableVenta.getModel();
        int fila = TableVenta.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }
    private void LimpiarClienteVenta(){
       txtRucVenta.setText("");
       txtNombreClienteVenta.setText("");
       txtTelefonoCV.setText("");
       txtDireccionCV.setText("");
       txtRazonCV.setText("");   
    }
    private  void pdf(){
        try {
            int id = vDao.IdVenta();
            FileOutputStream archivo;
            File file = new File("src/pdf/venta"+id+".pdf");
            archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            Image img=Image.getInstance("src/img/upiicsa_3.png");
            
            Paragraph fecha= new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD,BaseColor.BLUE);
            fecha.add(Chunk.NEWLINE);
            Date date = new Date();
            fecha.add("Factura:"+id+"\n"+"Fecha: "+new SimpleDateFormat("dd-mm-yyyy").format(date)+"\n\n");
            
            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float [] ColumnaEncabezado=new float[]{20f,30f,70f,40f};
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            Encabezado.addCell(img);
            
            String ruc = txtRucConfig.getText();
            String nom = txtNombreConfig.getText();
            String tel = txtTelefonoConfig.getText();
            String dir = txtDireccionConfig.getText();
            String ra  = txtRazonConfig.getText();       
            
            Encabezado.addCell("");
            Encabezado.addCell("Ruc: "+ruc+"\nNombre: "+nom+"\nTelefono: "+tel+"\nDirección: "+dir+"\nRazón: "+ra);
            Encabezado.addCell(fecha);
            doc.add(Encabezado);
            
            Paragraph cliente = new Paragraph();
            cliente.add(Chunk.NEWLINE);
            cliente.add("Datos de el Cliente:"+"\n\n");
            doc.add(cliente);
            
            PdfPTable tablacliente= new PdfPTable(4);
            tablacliente.setWidthPercentage(100);
            tablacliente.getDefaultCell().setBorder(0);
            float [] ColumnaCliente=new float[]{20f,50f,30f,40f};
            tablacliente.setWidths(ColumnaCliente);
            tablacliente.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cl1=new PdfPCell(new Phrase("Dni/Ruc",negrita));
            PdfPCell cl2=new PdfPCell(new Phrase("Nombre",negrita));
            PdfPCell cl3=new PdfPCell(new Phrase("Telefono",negrita));
            PdfPCell cl4=new PdfPCell(new Phrase("Direccion",negrita));
            cl1.setBorder(0);
            cl2.setBorder(0);
            cl3.setBorder(0);
            cl4.setBorder(0);
            tablacliente.addCell(cl1);
            tablacliente.addCell(cl2);
            tablacliente.addCell(cl3);
            tablacliente.addCell(cl4);
            tablacliente.addCell(txtRucVenta.getText());
            tablacliente.addCell(txtNombreClienteVenta.getText());
            tablacliente.addCell(txtTelefonoCV.getText());
            tablacliente.addCell(txtDireccionCV.getText());
            
            doc.add(tablacliente);
            
            //Productos
            
            PdfPTable tablapro= new PdfPTable(4);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            float [] ColumnaPro=new float[]{10f,50f,15f,20f};
            tablapro.setWidths(ColumnaPro);
            tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell pro1=new PdfPCell(new Phrase("Cant.",negrita));
            PdfPCell pro2=new PdfPCell(new Phrase("Descripción",negrita));
            PdfPCell pro3=new PdfPCell(new Phrase("Precio U.",negrita));
            PdfPCell pro4=new PdfPCell(new Phrase("Precio T.",negrita));
            pro1.setBorder(0);
            pro2.setBorder(0);
            pro3.setBorder(0);
            pro4.setBorder(0);
            pro1.setBackgroundColor(BaseColor.YELLOW);
            pro2.setBackgroundColor(BaseColor.YELLOW);
            pro3.setBackgroundColor(BaseColor.GREEN);
            pro4.setBackgroundColor(BaseColor.GREEN);
            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);
            for (int i = 0; i < TableVenta.getRowCount(); i++) {
                String producto = TableVenta.getValueAt(i, 1).toString();
                String cantidad = TableVenta.getValueAt(i, 2).toString();
                String precio = TableVenta.getValueAt(i, 3).toString();
                String total = TableVenta.getValueAt(i, 4).toString();
                tablapro.addCell(cantidad);
                tablapro.addCell(producto);
                tablapro.addCell(precio);
                tablapro.addCell(total);
            }
            doc.add(tablapro);
            
            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total a pagar: "+Totalpagar);
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);
            
            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Cancelacion y Firma\n\n");
            firma.add("-----------------------");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);
            
            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("Gracias por su Compra\n\n");
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);
            
            
            doc.close();
            archivo.close();
            Desktop.getDesktop().open(file);
            
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }       
    }
}
