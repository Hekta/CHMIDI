package com.hekta.chmidi.util;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;

import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Transmitter;

/*
*
* @author Hekta
*/
public class CHMIDISequencer {

	private final String id;
	private final Sequencer MIDISequencer;
	private final Transmitter MIDITransmitter;
	private final CHMIDINotePlayer notePlayer;

	private boolean paused = false;

	public CHMIDISequencer(String id, File file, CHMIDINotePlayer player, Target t) {
		try {
			this.MIDISequencer = MidiSystem.getSequencer(false);
			this.MIDISequencer.setSequence(MidiSystem.getSequence(file));
			this.MIDITransmitter = this.MIDISequencer.getTransmitter();
			this.MIDITransmitter.setReceiver(player);
			this.notePlayer = player;
			this.id = id;
		} catch (IOException exception) {
				throw new ConfigRuntimeException(exception.getMessage(), ExceptionType.IOException, t);
		} catch (InvalidMidiDataException exception) {
				throw new ConfigRuntimeException(exception.getMessage(), ExceptionType.FormatException, t);
		} catch (MidiUnavailableException exception) {
				throw new ConfigRuntimeException(exception.getMessage(), ExceptionType.PluginInternalException, t);
		}
	}

	public CHMIDISequencer(String id, InputStream stream, CHMIDINotePlayer player, Target t) {
		try {
			this.MIDISequencer = MidiSystem.getSequencer(false);
			this.MIDISequencer.setSequence(stream);
			this.MIDITransmitter = this.MIDISequencer.getTransmitter();
			this.MIDITransmitter.setReceiver(player);
			this.notePlayer = player;
			this.id = id;
		} catch (IOException exception) {
				throw new ConfigRuntimeException(exception.getMessage(), ExceptionType.IOException, t);
		} catch (InvalidMidiDataException exception) {
				throw new ConfigRuntimeException(exception.getMessage(), ExceptionType.FormatException, t);
		} catch (MidiUnavailableException exception) {
				throw new ConfigRuntimeException(exception.getMessage(), ExceptionType.PluginInternalException, t);
		}
	}

	public CHMIDISequencer(String id, Sequence sequence, CHMIDINotePlayer player, Target t) {
		try {
			this.MIDISequencer = MidiSystem.getSequencer(false);
			this.MIDISequencer.setSequence(sequence);
			this.MIDITransmitter = this.MIDISequencer.getTransmitter();
			this.MIDITransmitter.setReceiver(player);
			this.notePlayer = player;
			this.id = id;
		} catch (InvalidMidiDataException exception) {
				throw new ConfigRuntimeException(exception.getMessage(), ExceptionType.FormatException, t);
		} catch (MidiUnavailableException exception) {
				throw new ConfigRuntimeException(exception.getMessage(), ExceptionType.PluginInternalException, t);
		}
	}

	public String getID() {
		return this.id;
	}

	public CHMIDINotePlayer getNotePlayer() {
		return this.notePlayer;
	}

	public Sequencer getMIDISequencer() {
		return this.MIDISequencer;
	}

	public Transmitter getMIDITransmitter() {
		return this.MIDITransmitter;
	}

	public void start() {
		this.MIDISequencer.start();
	}

	public void stop() {
		this.paused = false;
		this.MIDISequencer.stop();
	}

	public boolean isPaused() {
		return this.paused;
	}

	public void setPaused(boolean paused) {
		if (paused) {
			this.paused = true;
			this.MIDISequencer.stop();
		} else {
			this.MIDISequencer.start();
			this.paused = false;
		}
	}

	public void open() {
		try {
			this.MIDISequencer.open();
		} catch (MidiUnavailableException exception) {
			throw new ConfigRuntimeException(exception.getMessage(), ExceptionType.PluginInternalException, Target.UNKNOWN);
		}
	}

	public void close() {
		this.notePlayer.close();
		this.MIDITransmitter.close();
		this.MIDISequencer.close();
	}
}
