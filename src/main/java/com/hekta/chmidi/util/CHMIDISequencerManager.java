package com.hekta.chmidi.util;

import java.util.HashMap;

import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;

/*
*
* @author Hekta
*/
public final class CHMIDISequencerManager {

	private static final HashMap<String, CHMIDISequencer> sequencers = new HashMap<String, CHMIDISequencer>();

	public static boolean sequencerExists(String id) {
		return sequencers.keySet().contains(id);
	}

	public static CHMIDISequencer getSequencer(String id, Target t) {
		CHMIDISequencer sequencer = sequencers.get(id);
		if (sequencer == null) {
			throw new ConfigRuntimeException("'" + id + "' is not an existing sequence.", ExceptionType.PluginInternalException, t);
		} else {
			return sequencer;
		}
	}

	public static HashMap<String, CHMIDISequencer> getSequencers() {
		return sequencers;
	}

	public static void addSequencer(CHMIDISequencer sequencer, Target t) {
		if (sequencers.get(sequencer.getID()) == null) {
			sequencers.put(sequencer.getID(), sequencer);
		} else {
			throw new ConfigRuntimeException("A sequence with the given id ('" + sequencer.getID() + "') already exists.", ExceptionType.PluginInternalException, t);
		}
	}

	public static void deleteSequencer(String id, Target t) {
		CHMIDISequencer sequencer = sequencers.get(id);
		if (sequencer == null) {
			throw new ConfigRuntimeException("'" + id + "' is not an existing sequence.", ExceptionType.PluginInternalException, t);
		} else {
			sequencer.stop();
			sequencer.close();
			sequencers.remove(id);
		}
	}

	public static void deleteSequencer(CHMIDISequencer sequencer, Target t) {
		if (sequencers.get(sequencer.getID()) == null) {
			throw new ConfigRuntimeException("'" + sequencer.getID() + "' is not an existing sequence.", ExceptionType.PluginInternalException, t);
		} else {
			sequencer.stop();
			sequencer.close();
			sequencers.remove(sequencer.getID());
		}
	}

	public static void clear() {
		for (String sequencerID : sequencers.keySet()) {
			sequencers.get(sequencerID).stop();
			sequencers.get(sequencerID).close();
			sequencers.remove(sequencerID);
		}
	}
}
