package j.pivate.main.theClub.JNA;


import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

public class MemoryTool {
	
	static MyKernel32 kernel32 = (MyKernel32) Native.loadLibrary("kernel32", MyKernel32.class);  
    static MyUser32 user32 = (MyUser32) Native.loadLibrary("user32", MyUser32.class);  
    public static final int READ_RIGHT = 0x0010;  
    public static final int WRITE_RIGHT = 0x0020;  

    public static final int PROCESS_TERMINATE                                       =0x00000001;
    public static final int PROCESS_CREATE_THREAD                           =0x00000002;
    public static final int PROCESS_VM_OPERATION                            =0x00000008;
    public static final int PROCESS_VM_READ                                 =0x00000010;
    public static final int PROCESS_VM_WRITE                                        =0x00000020;
    public static final int PROCESS_DUP_HANDLE                              =0x00000040;
    public static final int PROCESS_CREATE_PROCESS                  =0x00000080;
    public static final int PROCESS_SET_QUOTA                                       =0x00000100;
    public static final int PROCESS_SET_INFORMATION                 =0x00000200;
    public static final int PROCESS_QUERY_INFORMATION                       =0x00000400;
    public static final int PROCESS_SUSPEND_RESUME                  =0x00000800;
    public static final int PROCESS_QUERY_LIMITED_INFORMATION       =0x00001000;

    public static final int DELETE                                                  =0x00010000;
    public static final int READ_CONTROL                                            =0x00020000;
    public static final int WRITE_DAC                                                       =0x00040000;
    public static final int WRITE_OWNER                                             =0x00080000;
    public static final int SYNCHRONIZE                                             =0x00100000;

    public static final int PROCESS_ALL_ACCESS                              =0x001F0FFF;
    ////////////////////////////////////////////////////////////////////////

    public static final int TH32CS_SNAPHEAPLIST                             =0x00000001;
    public static final int TH32CS_SNAPPROCESS                              =0x00000002;
    public static final int TH32CS_SNAPTHREAD                                       =0x00000004;
    public static final int TH32CS_SNAPMODULE                                       =0x00000008;    
    public static final int TH32CS_SNAPALL                                  =0x0000000F;
    
    public static final int TH32CS_SNAPMODULE32                             =0x00000010;
    public static final int TH32CS_INHERIT                                  =0x80000000;

  
    public static HANDLE openProcess(int permissions, int pid) {  
        HANDLE process = kernel32.OpenProcess(permissions, true, pid);  
        return process;  
    } 
    
    public static int getProcessId(HANDLE handle){
    	return kernel32.GetProcessId(handle);
    }
      
    public static int getProcessIdByWindow(String window) {  
        IntByReference pid = new IntByReference(0);
        user32.GetWindowThreadProcessId(user32.FindWindowA(null, window), pid);  
        return pid.getValue();  
    }
  
  
    public static Memory readMemory(HANDLE process, int address, int bytesToRead) {  
        IntByReference read = new IntByReference(0);  
        Memory output = new Memory(4); 
        kernel32.ReadProcessMemory(process, address, output, bytesToRead, read);  
        return output;  
    }  

}  
