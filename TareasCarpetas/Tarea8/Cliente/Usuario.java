public class Usuario{
    private String email;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String fecha_nacimiento;
    private String telefono;
    private String genero;
    private byte[] foto;
    
    public Usuario(String ema, String nom, String apeP, String apeM, String fec, String tel, String gen){
        this.email = ema;
        this.nombre = nom;
        this.apellido_paterno = apeP;
        this.apellido_materno = apeM;
        this.fecha_nacimiento = fec;
        this.telefono = tel;
        this.genero = gen;
    }

    public void mostrar(){
        System.out.println("Email: "+this.email);
        System.out.println("Nombre: "+this.nombre);
        System.out.println("Apellido paterno: "+this.apellido_paterno);
        System.out.println("Apellido materno: "+this.apellido_materno);
        System.out.println("Telefono: "+this.telefono);
        System.out.println("Genero: "+this.genero);
    }

    
}