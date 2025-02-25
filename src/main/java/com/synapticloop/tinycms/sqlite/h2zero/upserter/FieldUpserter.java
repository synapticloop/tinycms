package com.synapticloop.tinycms.sqlite.h2zero.upserter;

// - - - - thoughtfully generated by synapticloop h2zero - - - - 
//    with the use of synapticloop templar templating language
//                (java-create-upserter.templar)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import com.synapticloop.h2zero.base.manager.sqlite3.ConnectionManager;
import com.synapticloop.h2zero.util.LruCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.synapticloop.tinycms.sqlite.h2zero.model.util.Constants;

import com.synapticloop.tinycms.sqlite.h2zero.model.Field;

public class FieldUpserter {
	// the binder is unused in code, but will generate compile problems if this 
	// class is no longer referenced in the h2zero file. Just a nicety for
	// removing dead code
	@SuppressWarnings("unused")
	private static final String BINDER = Constants.FIELD_BINDER;

	private static final Logger LOGGER = LoggerFactory.getLogger(FieldUpserter.class);
	private static final String SQL_SELECT_START = 
		"""
			select 
				id_field, 
				id_field_type, 
				id_collection, 
				nm_field
			from 
				field
		""";
	private static final String SQL_BUILTIN_FIND_BY_PRIMARY_KEY = SQL_SELECT_START + " where id_field = ?";


	private FieldUpserter() {}

}