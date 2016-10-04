package j.pivate.main;

import java.util.List;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

import j.pivate.main.theClub.JNA.Kernel32;
import j.pivate.main.theClub.JNA.MemoryTool;
import j.pivate.main.theClub.JNA.Module;
import j.pivate.main.theClub.JNA.PsapiTools;
import j.pivate.main.theClub.JNA.User32;

public abstract class CheatEngine {

	public static Kernel32 kernel32 = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);  
	public static User32 user32 = (User32) Native.loadLibrary("user32", User32.class);  
	public static int readRight = 0x0010;
	
	private int pid;
	private Pointer readprocess;
	private int baseAddress;
	private int size = 4;
	public CheatEngine(String name, String exename){
		this.pid=  getProcessId(name);
		System.out.println("pid"+pid);
		this.baseAddress = getBaseAddress(pid,exename);
		
		readprocess = openProcess(readRight, pid);
	}
	
	public Memory readMemory(int address) {  
		IntByReference read = new IntByReference(0);  
		Memory output = new Memory(size);  
		kernel32.ReadProcessMemory(readprocess, address, output, size, read);  
		return output;  
	}
	
	
    public Integer getBaseAddress(int pid, String exeName){
	 	try {
			HANDLE game = MemoryTool.openProcess(MemoryTool.PROCESS_ALL_ACCESS, pid);
			List<Module> modules = PsapiTools.getInstance().EnumProcessModulesEx(game, 0x01);
			for (Module module : modules) {
				if(module.getBaseName().equals(exeName)){		
					if(module.getEntryPoint() != null){
						return ((Long)Pointer.nativeValue(module.getLpBaseOfDll().getPointer())).intValue();
					}
				}
			}
		} catch (Exception e) {}
    	return null;
    }
    
    public int getProcessId(String window) {  
        IntByReference pid = new IntByReference(0);  
        user32.GetWindowThreadProcessId(user32.FindWindowA(null, window), pid);  
        return pid.getValue();  
    }  
    
    public Pointer openProcess(int permissions, int pid) {  
        Pointer process = kernel32.OpenProcess(permissions, true, pid);  
        return process;  
    }  
    
    public Integer getAddress(int[] offsets){
    	int address = baseAddress+offsets[0];
    	Memory read;
    	for (int i = 1; i < offsets.length; i++) {
    		read = readMemory(address);
    		address = read.getInt(0)+offsets[i]; 
    		System.out.println(Integer.toHexString(address));
		}
    	
    	System.out.println("done");
    	return address;
    }
    
    
}
