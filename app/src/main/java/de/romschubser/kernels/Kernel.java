package de.romschubser.kernels;

public interface Kernel {
    public String getVersion();
    public String getAvailableVersion();
    public boolean isNewest();
    public String getKernel();
}
