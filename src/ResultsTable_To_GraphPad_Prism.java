/**
  * ResultsTable_To_GraphPad_Prism.java v1, 1 fev. 2017
    Fabrice P Cordelieres, fabrice.cordelieres at gmail.com
    
    Copyright (C) 2017 Fabrice P. Cordelieres
  
    License:
    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.ArrayList;

import ij.IJ;
import ij.io.SaveDialog;
import ij.measure.ResultsTable;
import ij.plugin.PlugIn;
import io.prismOutput;

public class ResultsTable_To_GraphPad_Prism implements PlugIn {

	@Override
	public void run(String arg0) {
		ResultsTable rt = ResultsTable.getResultsTable();
		if (rt.getCounter()!=0) {
			SaveDialog sd = new SaveDialog("Where to save exported pzfx file ?", "Exported_ResultsTable", ".pzfx");
			String dir=sd.getDirectory();
			String file=sd.getFileName();
			String path=dir+file;
			
			if(dir!=null){
				// Prepare data for export
				ArrayList<String> headers = new ArrayList<String>();
				String[] headings=rt.getHeadings();
				for (int i = 0; i < headings.length; i++) headers.add(headings[i]);
				
				ArrayList<String[]> data = new ArrayList<String[]>();
				for (int i = 0; i < rt.size(); i++) {
					String[] currLine = new String[headers.size()];
					for (int j = 0; j < headers.size(); j++)  currLine[j] = rt.getStringValue(headers.get(j), i);
					data.add(currLine);
				}
	
				// Export data
				prismOutput plzx = new prismOutput();
				plzx.addTable("ResultsTable", headers, data);
				plzx.write(path);
	
				// End message
				IJ.showStatus("ResultsTable exported as a pzfx file");
			}else{
				IJ.showStatus("ResultsTable export as a pzfx file canceled");
			}
		} else {
			IJ.showStatus("No ResultsTable to export");
		}
	}
}
