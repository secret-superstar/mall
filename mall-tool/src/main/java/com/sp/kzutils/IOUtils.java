package com.kuaizi.etl.common.util;

import java.io.Closeable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IOUtils {
	private static final Log LOG = LogFactory.getLog(IOUtils.class);

	public static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (Exception ex) {
				LOG.error(ex.getMessage(), ex);
			}
		}
	}
}
