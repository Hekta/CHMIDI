package com.hekta.chmidi;

import com.laytonsmith.annotations.shutdown;
import com.laytonsmith.annotations.startup;

import com.hekta.chmidi.util.CHMIDISequencerManager;

/*
 *
 * @author Hekta
 */
public class main {

	@startup
	public static void onEnable(){
		System.out.println("[CommandHelper] CHMIDI 1.0 loaded.");
	}

	@shutdown
	public static void onDisable(){
		CHMIDISequencerManager.clear();
		System.out.println("[CommandHelper] CHMIDI unloaded.");
	}
}
