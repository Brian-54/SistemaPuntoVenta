
package Modelo;

public class Venta {
    private int dni;
    private String cliente;
    private String vendedor;
    private double total;
    
    public Venta(){
        
    }
    public Venta(int dni,String cliente, String vendedor, double total){
        this.dni=dni;
        this.cliente=cliente;
        this.vendedor=vendedor;
        this.total=total;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
       
}
