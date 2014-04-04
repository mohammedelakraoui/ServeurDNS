import java.io.InputStream;

/**
 * Created by server-pc on 04/04/14.
 */
public class Dnsclient {

    private String NameDomaine="";
    private String AddIP="";
    private int Port;
    private Dnsserver server=new Dnsserver();
    private InputStream is;

    public Dnsclient(String NameDomaine,String AddIP,int Port)
    {
        this.NameDomaine=NameDomaine;
        this.AddIP=AddIP;
        this.Port=Port;
    }

    public void ResolutDirect()
    {


    }
    public void ResoultInverse()
    {

    }
    public String getAddIP() {
        return AddIP;
    }

    public void setAddIP(String addIP) {
        AddIP = addIP;
    }

    public String getNameDomaine() {
        return NameDomaine;
    }

    public void setNameDomaine(String nameDomaine) {
        NameDomaine = nameDomaine;
    }
    public int getPort() {
        return Port;
    }

    public void setPort(int port) {
        Port = port;
    }



}
