package de.romschubser.roms;

public interface Rom {
    public String getVersion();
    public String getAvailableVersion();
    public boolean isNewest();
    public String getRom();
}
