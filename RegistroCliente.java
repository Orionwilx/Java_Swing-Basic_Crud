import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegistroCliente {

    // Método para conectar a PostgreSQL (igual que antes)
    private static Connection conectar() {
        Connection conexion = null;
        try {
            // Cambia los parámetros según tu configuración
            String url = "jdbc:postgresql://localhost:5432/ipstest";
            String usuario = "postgres";
            String password = "123456";
            conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexión exitosa");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

    // Método para registrar un cliente (igual que antes)
    private static void registrarCliente(int document, String name, String lastName, String gener, long number,
            String email, String address) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                String query = "INSERT INTO client (document, name, lastname, gener, number, email, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conexion.prepareStatement(query);
                stmt.setInt(1, document);
                stmt.setString(2, name);
                stmt.setString(3, lastName);
                stmt.setString(4, gener);
                stmt.setLong(5, number);
                stmt.setString(6, email);
                stmt.setString(7, address);
                stmt.executeUpdate();
                stmt.close();
                conexion.close();
                JOptionPane.showMessageDialog(null, "Cliente registrado correctamente");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al registrar el cliente");
            }
        }
    }

    // Método para obtener todos los clientes (igual que antes)
    private static String obtenerClientes() {
        Connection conexion = conectar();
        StringBuilder clientes = new StringBuilder();
        if (conexion != null) {
            try {
                String query = "SELECT * FROM client";
                Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    clientes.append("ID: ").append(rs.getInt("id")).append(", ");
                    clientes.append("Nombre: ").append(rs.getString("name")).append(", ");
                    clientes.append("Apellido: ").append(rs.getString("lastname")).append(", ");
                    clientes.append("Correo: ").append(rs.getString("email")).append("\n");
                }
                stmt.close();
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al obtener los clientes");
            }
        }
        return clientes.toString();
    }

    // Método para buscar cliente por ID
    private static Cliente buscarCliente(int id) {
        Connection conexion = conectar();
        Cliente cliente = null;
        if (conexion != null) {
            try {
                String query = "SELECT * FROM client WHERE id = ?";
                PreparedStatement stmt = conexion.prepareStatement(query);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int document = rs.getInt("document");
                    String name = rs.getString("name");
                    String lastName = rs.getString("lastname");
                    String gener = rs.getString("gener");
                    long number = rs.getLong("number");
                    String email = rs.getString("email");
                    String address = rs.getString("address");
                    cliente = new Cliente(id, document, name, lastName, gener, number, email, address);
                }
                stmt.close();
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al buscar el cliente");
            }
        }
        return cliente;
    }

    // Método para actualizar un cliente
    private static void actualizarCliente(Cliente cliente) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                String query = "UPDATE client SET document = ?, name = ?, lastname = ?, gener = ?, number = ?, email = ?, address = ? WHERE id = ?";
                PreparedStatement stmt = conexion.prepareStatement(query);
                stmt.setInt(1, cliente.getDocument());
                stmt.setString(2, cliente.getName());
                stmt.setString(3, cliente.getLastName());
                stmt.setString(4, cliente.getGener());
                stmt.setLong(5, cliente.getNumber());
                stmt.setString(6, cliente.getEmail());
                stmt.setString(7, cliente.getAddress());
                stmt.setInt(8, cliente.getId());
                stmt.executeUpdate();
                stmt.close();
                conexion.close();
                JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al actualizar el cliente");
            }
        }
    }

    // Método para eliminar un cliente
    private static void eliminarCliente(int id) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                String query = "DELETE FROM client WHERE id = ?";
                PreparedStatement stmt = conexion.prepareStatement(query);
                stmt.setInt(1, id);
                stmt.executeUpdate();
                stmt.close();
                conexion.close();
                JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al eliminar el cliente");
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestión de Clientes");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Panel de Registro de Clientes (igual que antes)
        JPanel panelRegistro = new JPanel();
        panelRegistro.setLayout(null);
        // Crear los campos para registrar clientes
        JLabel documentLabel = new JLabel("Documento:");
        documentLabel.setBounds(10, 10, 80, 25);
        panelRegistro.add(documentLabel);

        JTextField documentText = new JTextField();
        documentText.setBounds(100, 10, 160, 25);
        panelRegistro.add(documentText);

        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setBounds(10, 40, 80, 25);
        panelRegistro.add(nameLabel);

        JTextField nameText = new JTextField();
        nameText.setBounds(100, 40, 160, 25);
        panelRegistro.add(nameText);

        JLabel lastNameLabel = new JLabel("Apellido:");
        lastNameLabel.setBounds(10, 70, 80, 25);
        panelRegistro.add(lastNameLabel);

        JTextField lastNameText = new JTextField();
        lastNameText.setBounds(100, 70, 160, 25);
        panelRegistro.add(lastNameText);

        JLabel generLabel = new JLabel("Género:");
        generLabel.setBounds(10, 100, 80, 25);
        panelRegistro.add(generLabel);

        JTextField generText = new JTextField();
        generText.setBounds(100, 100, 160, 25);
        panelRegistro.add(generText);

        JLabel numberLabel = new JLabel("Teléfono:");
        numberLabel.setBounds(10, 130, 80, 25);
        panelRegistro.add(numberLabel);

        JTextField numberText = new JTextField();
        numberText.setBounds(100, 130, 160, 25);
        panelRegistro.add(numberText);

        JLabel emailLabel = new JLabel("Correo:");
        emailLabel.setBounds(10, 160, 80, 25);
        panelRegistro.add(emailLabel);

        JTextField emailText = new JTextField();
        emailText.setBounds(100, 160, 160, 25);
        panelRegistro.add(emailText);

        JLabel addressLabel = new JLabel("Dirección:");
        addressLabel.setBounds(10, 190, 80, 25);
        panelRegistro.add(addressLabel);

        JTextField addressText = new JTextField();
        addressText.setBounds(100, 190, 160, 25);
        panelRegistro.add(addressText);

        // Botón de registro
        JButton registrarButton = new JButton("Registrar");
        registrarButton.setBounds(100, 230, 100, 25);
        panelRegistro.add(registrarButton);

        registrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int document = Integer.parseInt(documentText.getText());
                    String name = nameText.getText();
                    String lastName = lastNameText.getText();
                    String gener = generText.getText();
                    long number = Long.parseLong(numberText.getText());
                    String email = emailText.getText();
                    String address = addressText.getText();

                    if (!name.isEmpty() && !lastName.isEmpty() && document != 0) {
                        registrarCliente(document, name, lastName, gener, number, email, address);
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, rellena los campos obligatorios");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El documento y el teléfono deben ser números válidos");
                }
            }
        });

        tabbedPane.addTab("Registro de Cliente", panelRegistro);

        // Panel para listar clientes (igual que antes)
        JPanel panelClientes = new JPanel();
        panelClientes.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        panelClientes.add(scrollPane, BorderLayout.CENTER);
        JButton obtenerClientesButton = new JButton("Obtener Clientes");
        panelClientes.add(obtenerClientesButton, BorderLayout.SOUTH);
        obtenerClientesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String clientes = obtenerClientes();
                textArea.setText(clientes);
            }
        });
        tabbedPane.addTab("Listar Clientes", panelClientes);

        // Panel para Editar Clientes
        JPanel panelEditar = new JPanel();
        panelEditar.setLayout(null);

        JLabel buscarIdLabel = new JLabel("ID del Cliente:");
        buscarIdLabel.setBounds(10, 10, 100, 25);
        panelEditar.add(buscarIdLabel);

        JTextField buscarIdText = new JTextField();
        buscarIdText.setBounds(120, 10, 160, 25);
        panelEditar.add(buscarIdText);

        JButton buscarButton = new JButton("Buscar");
        buscarButton.setBounds(300, 10, 80, 25);
        panelEditar.add(buscarButton);

        // Campos para editar cliente
        JLabel nameLabel_edit = new JLabel("Nombre:");
        nameLabel_edit.setBounds(10, 40, 80, 25);
        panelEditar.add(nameLabel_edit);

        JTextField nameText_edit = new JTextField();
        nameText_edit.setBounds(100, 40, 160, 25);
        panelEditar.add(nameText_edit);

        JLabel lastNameLabel_edit = new JLabel("Apellido:");
        lastNameLabel_edit.setBounds(10, 70, 80, 25);
        panelEditar.add(lastNameLabel_edit);

        JTextField lastNameText_edit = new JTextField();
        lastNameText_edit.setBounds(100, 70, 160, 25);
        panelEditar.add(lastNameText_edit);

        JLabel numberLabel_edit = new JLabel("Teléfono:");
        numberLabel_edit.setBounds(10, 100, 80, 25);
        panelEditar.add(numberLabel_edit);

        JTextField numberText_edit = new JTextField();
        numberText_edit.setBounds(100, 100, 160, 25);
        panelEditar.add(numberText_edit);

        JLabel emailLabel_edit = new JLabel("Correo:");
        emailLabel_edit.setBounds(10, 130, 80, 25);
        panelEditar.add(emailLabel_edit);

        JTextField emailText_edit = new JTextField();
        emailText_edit.setBounds(100, 130, 160, 25);
        panelEditar.add(emailText_edit);

        JLabel addressLabel_edit = new JLabel("Dirección:");
        addressLabel_edit.setBounds(10, 160, 80, 25);
        panelEditar.add(addressLabel_edit);

        JTextField addressText_edit = new JTextField();
        addressText_edit.setBounds(100, 160, 160, 25);
        panelEditar.add(addressText_edit);

        JLabel documentLabel_edit = new JLabel("Documento:");
        documentLabel_edit.setBounds(10, 190, 80, 25);
        panelEditar.add(documentLabel_edit);

        JTextField documentText_edit = new JTextField();
        documentText_edit.setBounds(100, 190, 160, 25);
        panelEditar.add(documentText_edit);

        // Agregar más campos (género, número, correo, dirección)...

        JButton actualizarButton = new JButton("Actualizar");
        actualizarButton.setBounds(100, 220, 120, 25);
        panelEditar.add(actualizarButton);

        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.setBounds(230, 220, 120, 25);
        panelEditar.add(eliminarButton);

        // Acción para buscar cliente
        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(buscarIdText.getText());
                    Cliente cliente = buscarCliente(id);
                    if (cliente != null) {
                        nameText_edit.setText(cliente.getName());
                        lastNameText_edit.setText(cliente.getLastName());
                        emailText_edit.setText(cliente.getEmail());
                        numberText_edit.setText(String.valueOf(cliente.getNumber()));
                        addressText_edit.setText(cliente.getAddress());

                        // Rellenar el resto de los campos...
                    } else {
                        JOptionPane.showMessageDialog(null, "Cliente no encontrado");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El ID debe ser un número válido");
                }
            }
        });

        // Acción para actualizar cliente
        actualizarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(buscarIdText.getText());
                    String name = nameText_edit.getText();
                    String lastName = lastNameText_edit.getText();
                    int document = Integer.parseInt(documentText_edit.getText());
                    Long numbre = Long.parseLong(numberText_edit.getText());
                    String email = emailText_edit.getText();
                    String direccion = addressText_edit.getText();
                    // las campos de edit
                    // Obtener los demás campos...
                    Cliente cliente = new Cliente(id, document, name, lastName, "", numbre, email, direccion);
                    actualizarCliente(cliente);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el cliente");
                }
            }
        });

        // Acción para eliminar cliente
        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(buscarIdText.getText());
                    eliminarCliente(id);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el cliente");
                }
            }
        });

        tabbedPane.addTab("Editar Cliente", panelEditar);

        // Mostrar la ventana
        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    // Clase Cliente
    public static class Cliente {
        private int id, document;
        private String name, lastName, gener, email, address;
        private long number;

        public Cliente(int id, int document, String name, String lastName, String gener, long number, String email,
                String address) {
            this.id = id;
            this.document = document;
            this.name = name;
            this.lastName = lastName;
            this.gener = gener;
            this.number = number;
            this.email = email;
            this.address = address;
        }

        public int getId() {
            return id;
        }

        public int getDocument() {
            return document;
        }

        public String getName() {
            return name;
        }

        public String getLastName() {
            return lastName;
        }

        public String getGener() {
            return gener;
        }

        public long getNumber() {
            return number;
        }

        public String getEmail() {
            return email;
        }

        public String getAddress() {
            return address;
        }
    }
}
