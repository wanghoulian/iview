package cn.gcd.sb.model;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import cn.gcd.sb.ipc.IControlService;

public class ControlService extends Service{

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return controlService;
    }

    private final IControlService.Stub controlService = new IControlService.Stub() {
        @Override
        public String getName() throws RemoteException {
            return "controlService:" + Process.myPid();
        }

        @Override
        public void setName(String name) throws RemoteException {

        }
    };
}
