import java.util.List;

public class Direccion implements Comparable<Direccion>{
    String calle;
    String codigo;
    String tipoVia;
    String codigoPostal;
    String municipio;

    public Direccion(String calle, String codigo, String tipoVia) {
        this.calle = calle;
        this.codigo = codigo;
        this.tipoVia = tipoVia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Direccion() {}


    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipoVia() {
        return tipoVia;
    }

    public void setTipoVia(String tipoVia) {
        this.tipoVia = tipoVia;
    }


    @Override
    public String toString() {
        return codigo + ";"  + tipoVia + ";" + calle + ";" + codigoPostal + ";" + municipio;
    }

    @Override
    public int compareTo(Direccion d) {
        if (getCalle() == null || d.getCalle() == null) {
            return 0;
        }
        return getCalle().compareTo(d.getCalle());
    }

}
