/*
 * The MIT License
 *
 * Copyright 2012 epeihuu.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.k.qing.jenkins.plugin.buildline.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author epeihuu
 */
public class JobNameUtil {
	/**
	 * Auto wrap job name into release, branch and job type, these sections are put into one list
	 * @param displayName
	 * @return
	 */
	public static ArrayList<String> autoWrapDisplayName(String displayName) {
		int width = 14;
		ArrayList<String> sections = new ArrayList<String>();
		if (displayName.length() > width) {

			String job_regex = "_(?i)(daily_build|ft_build|coverity|unittest|ut|ft_report|swdl|ft)(_test)*";
			Matcher job_matcher = Pattern.compile(job_regex).matcher(
					displayName);
			if (job_matcher.find()) {
				String job_name = job_matcher.group();
				sections.add(job_name);
				displayName = displayName.replaceFirst(job_name, "");
			}

			String branch_regex = "_(?i)(r|int|xft|lsv)";

			Matcher branch_matcher = Pattern.compile(branch_regex).matcher(
					displayName);
			if (branch_matcher.find()) {
				String branch = branch_matcher.group();
				int index = displayName.indexOf(branch);
				branch = displayName.substring(index);
				sections.add(0, branch);
				displayName = displayName.replaceFirst(branch, "");
			}

			String first_part = displayName;
			sections.add(0, first_part);
		} else {
			sections.add(displayName);
		}
		return sections;
	}

}
