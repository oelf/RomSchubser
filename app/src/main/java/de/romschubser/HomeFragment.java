package de.romschubser;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import de.romschubser.kernels.Franco;
import de.romschubser.kernels.Kernel;
import de.romschubser.roms.Cyanogenmod;
import de.romschubser.roms.Rom;

public class HomeFragment extends Fragment {

    private String romString = "";
    private String kernelString = "";

    private Rom rom = null;
    private Kernel kernel = null;

    private View rootView = null;

    private static HomeFragment instance = null;

	public HomeFragment(){}

    public static HomeFragment getInstance() {
        if(instance == null) {
            instance = new HomeFragment();
        }

        return instance;
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(this.rootView == null) {
            this.rootView = inflater.inflate(R.layout.fragment_home, container, false);
            this.initView(rootView);
        }

        return this.rootView;
    }

    private void initView(View view) {
        this.romString = this.readRomString();
        this.kernelString = this.readKernelString();

        this.setRomVersion();
        this.setKernelVersion();

        Container container1 = new Container(view, R.id.containerStatus);
        container1.setHeader("Status");
        container1.addEntry("Rom", this.rom.getRom()+" ("+this.rom.getVersion()+")");
        container1.addEntry("Kernel", this.kernel.getKernel()+" ("+this.kernel.getVersion()+")");

        Container container2 = new Container(view, R.id.containerInstallUpdate);
        container2.setHeader("Installieren/Update");
        container2.addCheckbox("Rom", this.rom.getAvailableVersion());
        container2.addCheckbox("Kernel", this.kernel.getAvailableVersion());
        container2.addButton("Download and Install");
    }

    private void setRomVersion() {
        PackageManager pm = getActivity().getPackageManager();
        if(pm.hasSystemFeature("com.cyanogenmod.android")) {
            Cyanogenmod cm = new Cyanogenmod(this.romString);
            this.rom = cm;
        }
    }

    private void setKernelVersion() {
        if(this.kernelString.toLowerCase().contains("franco.kernel")) {
            Franco fk = new Franco(this.kernelString);
            this.kernel = fk;
        }
    }

    public String readRomString() {
        String modVer = Functions.getSystemProperty("getprop ro.modversion");
        modVer = (modVer == null || modVer.length() == 0 ? "Unbekannt" : modVer);

        return modVer;
    }

    public String readKernelString() {
        String modVer = Functions.getSystemProperty("cat /proc/version");
        modVer = (modVer == null || modVer.length() == 0 ? "Unbekannt" : modVer);

        return modVer;
    }
}
