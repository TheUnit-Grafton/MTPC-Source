package appguru.net;

import java.io.IOException;

class Connection {
    public int port;
    public String ip;
    public java.net.Socket connection;
    public java.net.ServerSocket server;
    public java.net.Socket server_connection;
    public java.io.BufferedReader reader;
    public java.io.PrintWriter writer;
    
    public Connection(int i, String s) {
        super();
        this.port = i;
        label1: {
            if (s == null) {
                break label1;
            }
            this.ip = s;
            label0: {
                try {
                    this.connection = new java.net.Socket(s, i);
                    this.reader = new java.io.BufferedReader(new java.io.InputStreamReader(this.connection.getInputStream()));
                } catch(java.io.IOException ignoredException) {
                    break label0;
                }
                break label1;
            }
            appguru.info.Console.errorMessage(((Object)this).getClass().getName(), "UNKNOWN HOST");
        }
        try {
            this.server = new java.net.ServerSocket(i);
            this.server_connection = this.server.accept();
            this.writer = new java.io.PrintWriter(this.server_connection.getOutputStream(), true);
        } catch(java.io.IOException ignoredException0) {
            appguru.info.Console.errorMessage(((Object)this).getClass().getName(), "INVALID I/O");
        }
    }
    
    public String read() throws IOException {
        if (this.reader == null) {
            appguru.info.Console.errorMessage(((Object)this).getClass().getName(), "UNKNOWN IP");
            throw new IOException("PORT : "+Integer.toString(this.port)+" : ERROR TRIED TO READ FROM UNKNOWN IP");
        }
        return this.reader.readLine();
    }
    
    public void write(String s) {
        this.writer.println(s);
    }
}
