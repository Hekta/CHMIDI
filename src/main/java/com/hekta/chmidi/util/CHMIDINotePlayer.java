package com.hekta.chmidi.util;

import java.util.HashMap;

import org.bukkit.block.NoteBlock;
import org.bukkit.Instrument;
import org.bukkit.Note;

import com.laytonsmith.abstraction.enums.MCInstrument;
import com.laytonsmith.abstraction.enums.MCTone;
import com.laytonsmith.abstraction.MCNote;
import com.laytonsmith.abstraction.StaticLayer;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/*
*
* @author Hekta
*/
public class CHMIDINotePlayer implements Receiver {

	private final NoteBlock noteBlock;

	private final HashMap<Integer, Instrument> channels = new HashMap<Integer, Instrument>();

	private final Instrument[] mcInstrument = {
		null, Instrument.PIANO, Instrument.BASS_GUITAR, Instrument.BASS_DRUM, Instrument.SNARE_DRUM, Instrument.STICKS
	};

	private final int[] midiToMCInstrument = {
		1, 1, 1, 1, 1, 1, 1, 1,		//000-007
		1, 1, 1, 1, 1, 1, 1, 1,		//008-015
		1, 1, 1, 1, 1, 1, 1, 1,		//016-023
		1, 1, 1, 1, 1, 1, 1, 1,		//024-031
		2, 2, 2, 2, 2, 2, 2, 2,		//032-039
		1, 1, 1, 2, 1, 1, 1, 1,		//040-047
		1, 1, 1, 1, 1, 1, 1, 1,		//048-055
		1, 1, 1, 1, 1, 1, 1, 1,		//056-063
		1, 1, 1, 1, 1, 1, 1, 1,		//064-071
		1, 1, 1, 1, 1, 1, 1, 1,		//072-079
		1, 1, 1, 1, 1, 1, 1, 1,		//080-087
		1, 1, 1, 1, 1, 1, 1, 1,		//088-095
		1, 1, 1, 1, 1, 1, 1, 1,		//096-103
		2, 2, 2, 2, 1, 1, 1, 1,		//104-111
		5, 5, 5, 5, 3, 3, 3, 4,		//112-119
		3, 3, 3, 1, 1, 3, 4, 3,		//120-127
	};

	private final int[] midiToMCDrum = {
		0, 0, 0, 0, 0, 0, 0, 0,		//000-007
		0, 0, 0, 0, 0, 0, 0, 0,		//008-015
		0, 0, 0, 0, 0, 0, 0, 0,		//016-023
		0, 0, 0, 0, 0, 0, 0, 0,		//024-031
		0, 0, 0, 3, 3, 5, 4, 4,		//032-039
		4, 2, 5, 2, 5, 4, 5, 4,		//040-047
		4, 5, 4, 5, 5, 5, 4, 5,		//048-055
		5, 5, 4, 5, 4, 3, 4, 4,		//056-063
		3, 4, 3, 5, 5, 5, 5, 5,		//064-071
		5, 5, 5, 5, 5, 5, 5, 5,		//072-079
		5, 5, 0, 0, 0, 0, 0, 0,		//080-087
		0, 0, 0, 0, 0, 0, 0, 0,		//088-095
		0, 0, 0, 0, 0, 0, 0, 0,		//096-103
		0, 0, 0, 0, 0, 0, 0, 0,		//104-111
		0, 0, 0, 0, 0, 0, 0, 0,		//112-119
		0, 0, 0, 0, 0, 0, 0, 0,		//120-127
	};

	public CHMIDINotePlayer(NoteBlock noteBlock) {
		this.noteBlock = noteBlock;
	}

	public void send(MidiMessage message, long timeStamp) {
		int status = message.getStatus();
		if ((status >= ShortMessage.PROGRAM_CHANGE) && (status < (ShortMessage.PROGRAM_CHANGE + 16))) {
			ShortMessage m = (ShortMessage) message;
			channels.put(m.getChannel(), mcInstrument[midiToMCInstrument[m.getData1()]]);
		} else if ((status >= ShortMessage.NOTE_ON) && (status < (ShortMessage.NOTE_ON + 16))) {
			ShortMessage m = (ShortMessage) message;
			Instrument instrument;
			MCNote note = null;
			if (m.getChannel() == 9) {
				instrument = mcInstrument[midiToMCDrum[m.getData1()]];
				note = StaticLayer.GetConvertor().GetNote(0, MCTone.A, false);
			} else {
				instrument = channels.get(m.getChannel());
				int height = m.getData1() + 18;
				while (height > 24) {
					height = height - 24;
				}
				switch (height) {
					case 0:
						note = StaticLayer.GetConvertor().GetNote(0, MCTone.F, true);
						break;
					case 1:
						note = StaticLayer.GetConvertor().GetNote(0, MCTone.G, false);
						break;
					case 2:
						note = StaticLayer.GetConvertor().GetNote(0, MCTone.G, true);
						break;
					case 3:
						note = StaticLayer.GetConvertor().GetNote(0, MCTone.A, false);
						break;
					case 4:
						note = StaticLayer.GetConvertor().GetNote(0, MCTone.A, true);
						break;
					case 5:
						note = StaticLayer.GetConvertor().GetNote(0, MCTone.B, false);
						break;
					case 6:
						note = StaticLayer.GetConvertor().GetNote(0, MCTone.C, false);
						break;
					case 7:
						note = StaticLayer.GetConvertor().GetNote(0, MCTone.C, true);
						break;
					case 8:
						note = StaticLayer.GetConvertor().GetNote(0, MCTone.D, false);
						break;
					case 9:
						note = StaticLayer.GetConvertor().GetNote(0, MCTone.D, true);
						break;
					case 10:
						note = StaticLayer.GetConvertor().GetNote(0, MCTone.E, false);
						break;
					case 11:
						note = StaticLayer.GetConvertor().GetNote(0, MCTone.F, false);
						break;
					case 12:
						note = StaticLayer.GetConvertor().GetNote(1, MCTone.F, true);
						break;
					case 13:
						note = StaticLayer.GetConvertor().GetNote(1, MCTone.G, false);
						break;
					case 14:
						note = StaticLayer.GetConvertor().GetNote(1, MCTone.G, true);
						break;
					case 15:
						note = StaticLayer.GetConvertor().GetNote(1, MCTone.A, false);
						break;
					case 16:
						note = StaticLayer.GetConvertor().GetNote(1, MCTone.A, true);
						break;
					case 17:
						note = StaticLayer.GetConvertor().GetNote(1, MCTone.B, false);
						break;
					case 18:
						note = StaticLayer.GetConvertor().GetNote(1, MCTone.C, false);
						break;
					case 19:
						note = StaticLayer.GetConvertor().GetNote(1, MCTone.C, true);
						break;
					case 20:
						note = StaticLayer.GetConvertor().GetNote(1, MCTone.D, false);
						break;
					case 21:
						note = StaticLayer.GetConvertor().GetNote(1, MCTone.D, true);
						break;
					case 22:
						note = StaticLayer.GetConvertor().GetNote(1, MCTone.E, false);
						break;
					case 23:
						note = StaticLayer.GetConvertor().GetNote(1, MCTone.F, false);
						break;
					case 24:
						note = StaticLayer.GetConvertor().GetNote(2, MCTone.F, true);
						break;
				}
			}
			if (instrument != null) {
				noteBlock.play(instrument, (Note) note.getHandle());
			}
		}
	}

	public void close() {
	}
}
