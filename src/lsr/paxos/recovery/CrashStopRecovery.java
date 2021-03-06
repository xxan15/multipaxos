package lsr.paxos.recovery;

import static lsr.common.ProcessDescriptor.processDescriptor;

import java.io.IOException;

import lsr.paxos.SnapshotProvider;
import lsr.paxos.core.Paxos;
import lsr.paxos.storage.StateReplica;
import lsr.paxos.storage.Storage;

public class CrashStopRecovery extends RecoveryAlgorithm {

    private final Paxos paxos;

    public CrashStopRecovery(SnapshotProvider snapshotProvider)
            throws IOException {
        Storage storage = new StateReplica();
        if (storage.getView() % processDescriptor.numReplicas == processDescriptor.localId) {
            storage.setView(storage.getView() + 1);
        }
        
        paxos = new Paxos(snapshotProvider, storage);
    }

    public void start() throws IOException {
    	fireRecoveryListener();
    }

    public Paxos getPaxos() {
        return paxos;
    }
}
