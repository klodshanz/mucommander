/*
 * This file is part of muCommander, http://www.mucommander.com
 * Copyright (C) 2002-2009 Maxence Bernard
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

package com.mucommander.ui.action.impl;

import com.mucommander.ui.action.AbstractActionDescriptor;
import com.mucommander.ui.action.ActionCategories;
import com.mucommander.ui.action.ActionCategory;
import com.mucommander.ui.action.MuAction;
import com.mucommander.ui.action.ActionFactory;
import com.mucommander.ui.main.MainFrame;
import com.mucommander.ui.main.table.FileTable;

import java.util.Hashtable;

import javax.swing.KeyStroke;

/**
 * Marks/unmarks files in the active FileTable, from the currently selected row to the last row (inclusive).
 * The last row will also become the currently selected row.
 *
 * <p>The currently selected row's marked state determines whether the rows will be marked or unmarked : if the selected
 * row is marked, the rows will be unmarked and vice-versa.
 *
 * @author Maxence Bernard
 */
public class MarkUpToLastRowAction extends MuAction {

    public MarkUpToLastRowAction(MainFrame mainFrame, Hashtable properties) {
        super(mainFrame, properties);
    }

    public void performAction() {
        FileTable fileTable = mainFrame.getActiveTable();

        int selectedRow = fileTable.getSelectedRow();
        int lastRow = fileTable.getRowCount()-1;

        fileTable.setRangeMarked(selectedRow, lastRow, !fileTable.getFileTableModel().isRowMarked(selectedRow));
        fileTable.selectRow(lastRow);
    }
    
    public static class Factory implements ActionFactory {

		public MuAction createAction(MainFrame mainFrame, Hashtable properties) {
			return new MarkUpToLastRowAction(mainFrame, properties);
		}
    }
    
    public static class Descriptor extends AbstractActionDescriptor {
    	public static final String ACTION_ID = "MarkUpToLastRow";
    	
		public String getId() { return ACTION_ID; }

		public ActionCategory getCategory() { return ActionCategories.Selection; }

		public KeyStroke getDefaultAltKeyStroke() { return null; }

		public KeyStroke getDefaultKeyStroke() { return KeyStroke.getKeyStroke("shift END"); }
    }
}