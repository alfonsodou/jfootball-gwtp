/**
 * Copyright (c) 2001-2016 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package org.javahispano.jfootball.framework.common.host;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Robert Maupin (contributor)
 * @author Pavel Savara (contributor)
 */
public class CpuManager implements ICpuManager { // NO_UCD (use default)
	private static Logger logger = Logger.getLogger(CpuManager.class.getName());

	private final static int APPROXIMATE_CYCLES_ALLOWED = 6250;
	private final static int TEST_PERIOD_MILLIS = 5000;

	private long cpuConstant = -1;

	public CpuManager() { // NO_UCD (unused code)
	}

	public long getCpuConstant() {
		if (cpuConstant == -1) {
			if (cpuConstant == -1) {
				calculateCpuConstant();
			}
		}
		return cpuConstant;
	}

	public void calculateCpuConstant() {
		setCpuConstant();
		logger.log(Level.INFO,
				"Each team will be allowed a maximum of " + cpuConstant + " nanoseconds per turn on this system.");
	}

	private void setCpuConstant() {
		long count = 0;
		double d = 0;

		long start = System.currentTimeMillis();

		while (System.currentTimeMillis() - start < TEST_PERIOD_MILLIS) {
			d += Math.hypot(Math.sqrt(Math.abs(Math.log(Math.atan(Math.random())))),
					Math.cbrt(Math.abs(Math.random() * 10))) / Math.exp(Math.random());
			count++;
		}

		// to cheat optimizer, almost never happen
		if (d == 0.0) {
			logger.log(Level.INFO, "bingo!");
		}

		cpuConstant = Math.max(1, (long) (1000000.0 * APPROXIMATE_CYCLES_ALLOWED * TEST_PERIOD_MILLIS / count));
	}

}
