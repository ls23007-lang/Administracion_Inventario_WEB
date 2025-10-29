/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package Producto.vista;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
 // Para JComboBox
// Para JComboBox
import Producto.DAO.ProductoDAO; 
import Producto.DAO.CategoriaDAO; 
import Producto.DAO.ProveedorDAO; 
import Producto.modelo.Producto; 
import Producto.modelo.Item; 

/**
 *
 * @author ander
 */
public class RegistrarProducto extends javax.swing.JFrame {


   // DECLARACIONES
    private List<Item> categorias;
    private List<Item> proveedores;

    // INSTANCIACIÓN DE DAO (CORREGIDO)
    // ProductoDAO se instancia en los métodos donde se necesita (registrar, actualizar, etc.)
    private CategoriaDAO categoriaDAO = new CategoriaDAO(); 
    private ProveedorDAO proveedorDAO = new ProveedorDAO(); 

    /**
     * Constructor
     */
    public RegistrarProducto() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        //Cargar los IDs y Nombres de Categoría/Proveedor de la BD
        cargarDatosClaves(); // ⬅️ Nuevo método implementado abajo

        //Llenar cmbCategoria y cmbProveedor con los datos cargados
        inicializarComboBoxes();

        //Configurar el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Nombre", "Marca", "Modelo", "Categoría", "Proveedor", "Costo Unitario", "Cantidad" }
        ) {
             //la columna ID no editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; 
            }
        };

        //Asignar el modelo a la tabla
        tablaProductos.setModel(model); 

        //Cargar los datos REALES de la base de datos a la JTable
        cargarTablaProductos();
        
        //Listener para rellenar campos al hacer click en la tabla
        tablaProductos.addMouseListener(new MouseAdapter() {
             @Override
            public void mouseClicked(MouseEvent evt) {
                int fila = tablaProductos.getSelectedRow();
                if (fila >= 0) {
                    txtNombre.setText(tablaProductos.getValueAt(fila, 1).toString());
                    txtMarca.setText(tablaProductos.getValueAt(fila, 2).toString());
                    txtModelo.setText(tablaProductos.getValueAt(fila, 3).toString());
                    txtCostoUnitario.setText(tablaProductos.getValueAt(fila, 6).toString());
                    txtCantidad.setText(tablaProductos.getValueAt(fila, 7).toString()); 
                    

                    // Traducir nombre de Categoría y Proveedor de vuelta a la selección del ComboBox
                    seleccionarComboBoxPorNombre(cmbCategoria, tablaProductos.getValueAt(fila, 4).toString());
                    seleccionarComboBoxPorNombre(cmbProveedor, tablaProductos.getValueAt(fila, 5).toString());
                }
            }
        });
    }

    // -------------------------------------------------------------------------
    
    // -------------------------------------------------------------------------
    /**
     * Carga las listas de categorías y proveedores desde la BD.
     */
    private void cargarDatosClaves() {
        // Llama a los DAOs para llenar las listas internas
        categorias = categoriaDAO.obtenerTodos();
        proveedores = proveedorDAO.obtenerTodos();
    }
    
    // -------------------------------------------------------------------------
    // MÉTODOS AUXILIARES Y DE INICIALIZACIÓN
    // -------------------------------------------------------------------------

    // Método auxiliar para seleccionar el ComboBox por el nombre
    private void seleccionarComboBoxPorNombre(javax.swing.JComboBox<String> cmb, String nombre) {
        for (int i = 0; i < cmb.getItemCount(); i++) {
            if (cmb.getItemAt(i) != null && cmb.getItemAt(i).equals(nombre)) {
                cmb.setSelectedIndex(i);
                return;
            }
        }
    }

    private void inicializarComboBoxes() {
        cmbCategoria.removeAllItems();
        // Cargar las categorías obtenidas de la BD
        for (Item cat : categorias) {
            cmbCategoria.addItem(cat.getNombre());
        }
        cmbCategoria.setSelectedIndex(-1);

        cmbProveedor.removeAllItems();
        // Cargar los proveedores obtenidos de la BD
        for (Item prov : proveedores) {
            cmbProveedor.addItem(prov.getNombre());
        }
        cmbProveedor.setSelectedIndex(-1);
    }

    // MÉTODO: Cargar datos de la BD a la JTable
    private void cargarTablaProductos() {
        ProductoDAO productoDAO = new ProductoDAO();
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaProductos.getModel();
        
        // Limpiar filas anteriores antes de cargar
        modeloTabla.setRowCount(0);
        
        List<Producto> productos = productoDAO.obtenerTodosProductos();
        
        for (Producto p : productos) {
             // Se debe traducir el ID de Categoría/Proveedor a Nombre para mostrar
             // IMPORTANTE: Se usan las listas 'categorias' y 'proveedores' ya cargadas
            String catNombre = buscarNombreCategoria(p.getCategoriaId()); 
            String provNombre = buscarNombreProveedor(p.getProveedorId()); 
            
            modeloTabla.addRow(new Object[]{
                p.getId(), // ID REAL de la BD
                p.getNombre(),
                p.getMarca(),
                p.getModelo(),              
                catNombre, 
                provNombre,  
                p.getCostoUnitario(),
                p.getCantidad()
            });
        }
    }
    
    // 🟢 MÉTODOS AUXILIARES: Traducen ID a Nombre
    private String buscarNombreCategoria(int id) {
        for (Item cat : categorias) {
            if (cat.getId() == id) {
                return cat.getNombre();
            }
        }
        return "Desconocido";
    }

    private String buscarNombreProveedor(int id) {
        for (Item prov : proveedores) {
            if (prov.getId() == id) {
                return prov.getNombre();
            }
        }
        return "Desconocido";
    }

    // Método para limpiar los campos del formulario
    private void limpiarCampos() {
        txtNombre.setText("");
        txtMarca.setText("");
        cmbCategoria.setSelectedIndex(-1);
        cmbProveedor.setSelectedIndex(-1);
        txtCostoUnitario.setText("");
        txtBuscar.setText("");
        tablaProductos.clearSelection();
        txtModelo.setText("");
        txtCantidad.setText("");
    }




    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCostoUnitario = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        cmbCategoria = new javax.swing.JComboBox<>();
        cmbProveedor = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        panelTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registrar Producto");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("\"Nombre:\"");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel1, gridBagConstraints);

        txtNombre.setBackground(new java.awt.Color(51, 51, 51));
        txtNombre.setColumns(20);
        txtNombre.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(255, 255, 255));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtNombre, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("\"Categoría:\"");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("\"Proveedor:\"");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("\"Costo Unitario:\"");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel4, gridBagConstraints);

        txtCostoUnitario.setBackground(new java.awt.Color(51, 51, 51));
        txtCostoUnitario.setColumns(20);
        txtCostoUnitario.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtCostoUnitario, gridBagConstraints);

        btnRegistrar.setBackground(new java.awt.Color(255, 102, 51));
        btnRegistrar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel1.add(btnRegistrar, gridBagConstraints);

        btnLimpiar.setBackground(new java.awt.Color(204, 204, 204));
        btnLimpiar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel1.add(btnLimpiar, gridBagConstraints);

        btnCancelar.setBackground(new java.awt.Color(204, 204, 204));
        btnCancelar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel1.add(btnCancelar, gridBagConstraints);

        btnActualizar.setBackground(new java.awt.Color(255, 102, 51));
        btnActualizar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        jPanel1.add(btnActualizar, gridBagConstraints);

        btnEliminar.setBackground(new java.awt.Color(255, 102, 51));
        btnEliminar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        jPanel1.add(btnEliminar, gridBagConstraints);

        btnBuscar.setBackground(new java.awt.Color(255, 102, 51));
        btnBuscar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel1.add(btnBuscar, gridBagConstraints);

        txtBuscar.setBackground(new java.awt.Color(51, 51, 51));
        txtBuscar.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel1.add(txtBuscar, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("¿Qué producto deseas buscar?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("\"Marca:\"");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel6, gridBagConstraints);

        txtMarca.setBackground(new java.awt.Color(51, 51, 51));
        txtMarca.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtMarca, gridBagConstraints);

        cmbCategoria.setBackground(new java.awt.Color(51, 51, 51));
        cmbCategoria.setForeground(new java.awt.Color(255, 255, 255));
        cmbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbCategoria, gridBagConstraints);

        cmbProveedor.setBackground(new java.awt.Color(51, 51, 51));
        cmbProveedor.setForeground(new java.awt.Color(255, 255, 255));
        cmbProveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbProveedor, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("\"Modelo:\"");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel7, gridBagConstraints);

        txtModelo.setBackground(new java.awt.Color(51, 51, 51));
        txtModelo.setForeground(new java.awt.Color(255, 255, 255));
        txtModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtModeloActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtModelo, gridBagConstraints);

        txtCantidad.setBackground(new java.awt.Color(51, 51, 51));
        txtCantidad.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtCantidad, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("\"Cantidad:\"");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel8, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        panelTabla.setBackground(new java.awt.Color(102, 102, 102));
        panelTabla.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelTabla.setForeground(new java.awt.Color(255, 255, 255));
        panelTabla.setPreferredSize(new java.awt.Dimension(550, 250));
        panelTabla.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(580, 250));

        tablaProductos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Marca", "Modelo", "Categoria", "Proveedor", "Cantidad", "Costo Unitario"
            }
        ));
        tablaProductos.setRowHeight(25);
        jScrollPane1.setViewportView(tablaProductos);

        panelTabla.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelTabla, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
     String nombre = txtNombre.getText();
        String marca = txtMarca.getText();
        String modelo = txtModelo.getText();
        String precioTexto = txtCostoUnitario.getText();
        String cantidadTexto = txtCantidad.getText();

        int catIndex = cmbCategoria.getSelectedIndex();
        int provIndex = cmbProveedor.getSelectedIndex();

        // Validación de campos vacíos
        if (nombre.isEmpty() || marca.isEmpty()  || modelo.isEmpty()
                 || catIndex == -1 || provIndex == -1 || precioTexto.isEmpty() || cantidadTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.");
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadTexto);
            double precio = Double.parseDouble(precioTexto);

            // Validación de valores numéricos
            if (cantidad < 0) {
                JOptionPane.showMessageDialog(this, "La cantidad no puede ser negativa.");
                return;
            }
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor a 0.");
                return;
            }

            // Obtener los IDs reales de las claves foráneas
            //  Se usa .get(index) en lugar de [index] en una List
            Item categoriaSeleccionada = categorias.get(catIndex);
            Item proveedorSeleccionado = proveedores.get(provIndex);

            // Crear el objeto Producto sin ID (la BD lo asignará)
            Producto nuevoProducto = new Producto(
                nombre, marca, modelo, precio, cantidad, 
                categoriaSeleccionada.getId(), proveedorSeleccionado.getId()
            );

            // LLAMAR AL DAO PARA REGISTRAR EN LA BD
            ProductoDAO productoDAO = new ProductoDAO();

            if (productoDAO.registrarProducto(nuevoProducto)) {
                JOptionPane.showMessageDialog(this, "✅ Producto registrado correctamente en la Base de Datos.");
                
                // Recargar la tabla con los datos actualizados de la BD
                cargarTablaProductos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al registrar: Verifique la BD y la consola.", "Error de BD", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad y/o precio debe ser un número válido.");
        }
    
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        
    this.dispose();
        
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
       limpiarCampos();
    
    
    
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String criterio = txtBuscar.getText().trim();
    ProductoDAO productoDAO = new ProductoDAO();

    //Validación de campo vacío. Si está vacío, recarga toda la tabla.
    if (criterio.isEmpty()) {
        JOptionPane.showMessageDialog(this, "El campo de búsqueda estaba vacío. Mostrando todos los productos.");
        cargarTablaProductos(); 
        return;
    }

    // Llamar al DAO para obtener la lista filtrada desde la BD
    List<Producto> productosFiltrados = productoDAO.buscarProductosPorCriterio(criterio);

    // Obtener el modelo de la tabla y limpiar filas anteriores
    DefaultTableModel modeloTabla = (DefaultTableModel) tablaProductos.getModel();
    modeloTabla.setRowCount(0); 
    
    // Llenar la tabla con los resultados obtenidos de la BD
    if (productosFiltrados.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No se encontraron productos con el criterio: " + criterio, "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
    } else {
        for (Producto p : productosFiltrados) {
            //Se necesitan los métodos auxiliares para traducir ID a Nombre
            String catNombre = buscarNombreCategoria(p.getCategoriaId());
            String provNombre = buscarNombreProveedor(p.getProveedorId());

            modeloTabla.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getMarca(),
                p.getModelo(),
                catNombre, 
                provNombre, 
                p.getCostoUnitario(),
                p.getCantidad()
                
            });
        }
        JOptionPane.showMessageDialog(this, "Se encontraron " + productosFiltrados.size() + " producto(s).", "Búsqueda Exitosa", JOptionPane.INFORMATION_MESSAGE);
    }
    
    //  limpiar el campo de búsqueda después de mostrar los resultados
    txtBuscar.setText(""); 
        
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
     int fila = tablaProductos.getSelectedRow();
        if (fila >= 0) {
            
            // Obtener el ID del producto seleccionado (columna 0)
            int idProducto = (int) tablaProductos.getValueAt(fila, 0); 
            
            int catIndex = cmbCategoria.getSelectedIndex();
            int provIndex = cmbProveedor.getSelectedIndex();

            // Validación de campos de ComboBox
            if (catIndex == -1 || provIndex == -1 || txtNombre.getText().isEmpty() || txtMarca.getText().isEmpty() || txtModelo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete todos los campos (y seleccione Categoría/Proveedor).");
                return;
            }

            try {
                double precio = Double.parseDouble(txtCostoUnitario.getText());
                int cantidad = Integer.parseInt(txtCantidad.getText());

                if (cantidad < 0 || precio <= 0) {
                     JOptionPane.showMessageDialog(this, "Cantidad/Precio deben ser números válidos (>0).");
                     return;
                }

                 
                Item categoriaSeleccionada = categorias.get(catIndex);
                Item proveedorSeleccionado = proveedores.get(provIndex);
                
                // Crear el objeto Producto para enviar al DAO (ahora SÍ necesita el ID)
                Producto productoActualizado = new Producto(
                    idProducto, txtNombre.getText(), txtMarca.getText(), txtModelo.getText(), 
                     categoriaSeleccionada.getId(), proveedorSeleccionado.getId(), precio, cantidad
                );
                
                // LLAMAR AL DAO PARA ACTUALIZAR EN LA BD
                ProductoDAO productoDAO = new ProductoDAO();
                if (productoDAO.actualizarProducto(productoActualizado)) {
                    JOptionPane.showMessageDialog(this, "✅ Producto actualizado correctamente en la Base de Datos.");
                    cargarTablaProductos(); // Recargar la tabla
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al actualizar: revise la BD y la consola.", "Error de BD", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "La cantidad y/o precio debe ser un número válido.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para actualizar");
        }
    
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tablaProductos.getSelectedRow();
        if (fila >= 0) {
            // Obtener el ID del producto seleccionado (columna 0)
            int idProducto = (int) tablaProductos.getValueAt(fila, 0); 
            
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el producto ID: " + idProducto + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                // LLAMAR AL DAO PARA ELIMINAR EN LA BD
                ProductoDAO productoDAO = new ProductoDAO();
                if (productoDAO.eliminarProducto(idProducto)) {
                    JOptionPane.showMessageDialog(this, "✅ Producto eliminado correctamente de la Base de Datos.");
                    cargarTablaProductos(); // Recargar la tabla
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al eliminar: revise la BD y la consola.", "Error de BD", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtModeloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtModeloActionPerformed

  
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {}

        java.awt.EventQueue.invokeLater(() -> {
            new RegistrarProducto().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cmbCategoria;
    private javax.swing.JComboBox<String> cmbProveedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCostoUnitario;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}