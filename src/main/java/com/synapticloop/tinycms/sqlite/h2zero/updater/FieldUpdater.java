package com.synapticloop.tinycms.sqlite.h2zero.updater;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//              (java-create-updater.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Timestamp;

import com.synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import com.synapticloop.tinycms.sqlite.h2zero.model.util.Constants;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FieldUpdater {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.FIELD_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(FieldUpdater.class);

	// static fields generated by synapticloop h2zero
	private static final String SQL_UPDATE_START = "update field ";

	// static fields generated from the user input

	private FieldUpdater() {}

}