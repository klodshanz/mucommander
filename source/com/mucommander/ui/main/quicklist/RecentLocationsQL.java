/*
 * This file is part of muCommander, http://www.mucommander.com
 * Copyright (C) 2002-2008 Maxence Bernard
 *
 * muCommander is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * muCommander is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mucommander.ui.main.quicklist;

import com.mucommander.file.AbstractFile;
import com.mucommander.text.Translator;
import com.mucommander.ui.action.MuAction;
import com.mucommander.ui.action.ShowRecentLocationsQLAction;
import com.mucommander.ui.event.LocationEvent;
import com.mucommander.ui.event.LocationListener;
import com.mucommander.ui.quicklist.QuickListWithIcons;

import javax.swing.*;
import java.util.LinkedList;

/**
 * This quick list shows recently accessed locations.
 * 
 * @author Arik Hadas
 */
public class RecentLocationsQL extends QuickListWithIcons implements LocationListener{
	private static int MAX_ELEMENTS = 11;
	private LinkedList linkedList;

	public RecentLocationsQL() {
		super(MuAction.getStandardLabel(ShowRecentLocationsQLAction.class), Translator.get("recent_locations_quick_list.empty_message"));
		
		linkedList = new LinkedList();
	}

	protected void acceptListItem(Object item) {
		folderPanel.tryChangeCurrentFolder((AbstractFile)item);
	}

	public void locationCancelled(LocationEvent locationEvent) {}

	public void locationChanged(LocationEvent locationEvent) {
		AbstractFile currentFolder = locationEvent.getFolderPanel().getCurrentFolder();
			
		if (!linkedList.remove(currentFolder) && linkedList.size() >= MAX_ELEMENTS)
			linkedList.removeLast();
		linkedList.addFirst(currentFolder);
	}

	public void locationChanging(LocationEvent locationEvent) {}

	public void locationFailed(LocationEvent locationEvent) {}

	public Object[] getData() {
		LinkedList list = (LinkedList) linkedList.clone();

		if (!list.remove(folderPanel.getCurrentFolder()))
			list.removeLast();
		
		return list.toArray();
	}

	protected Icon itemToIcon(Object item) {
		return getIconOfFile((AbstractFile)item);
	}
}