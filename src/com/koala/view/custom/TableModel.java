package com.koala.view.custom;

import java.io.Serializable;
import java.util.Vector;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Vector<Vector<Object>> dataVector;
    protected Vector<String> columnIdentifiers;
    
    public TableModel() {
        dataVector = new Vector<Vector<Object>>();
        columnIdentifiers = new Vector<String>(15); 
        columnIdentifiers.addElement("i");
    	columnIdentifiers.addElement("예약");
    	columnIdentifiers.addElement("투숙");
    	columnIdentifiers.addElement("퇴실");
    	columnIdentifiers.addElement("입실일");
    	columnIdentifiers.addElement("퇴실일");
    	columnIdentifiers.addElement("호칭");
    	columnIdentifiers.addElement("성함");
    	columnIdentifiers.addElement("국가");
    	columnIdentifiers.addElement("여권");
    	columnIdentifiers.addElement("객실");
    	columnIdentifiers.addElement("호수");
    	columnIdentifiers.addElement("이메일");
    	columnIdentifiers.addElement("전화");
    	columnIdentifiers.addElement("요청");
    	fireTableStructureChanged();
    }
    
    public void setdataVector(Vector<Vector<Object>> dataVector) {
        this.dataVector = nonNullVector(dataVector);
        System.out.println("테이블바뀜"+dataVector.size());
        fireTableRowsInserted(0, this.dataVector.size());
    }

    public void addRow(Vector<Object> rowData) {
        insertRow(getRowCount(), rowData);
    }
	public void updateRow(int row, Vector<Object> v) {
		removeRow(row);
		insertRow(row, v);
	}
	public void removeRow(Vector<Object> v) {
		removeRow(dataVector.indexOf(v));
	}
	
    public void removeRow(int row) {
        dataVector.removeElementAt(row);
        fireTableRowsDeleted(row, row);
    }
    
    public void insertRow(int row, Vector<Object> rowData) {
        dataVector.insertElementAt(rowData, row);
        fireTableRowsInserted(row, row);
    }

	public void clearAllRows() {
		while(getRowCount()>0)
		{
			removeRow(0);
			fireTableRowsDeleted(0, 0);
		}
	}   

	public Object getValueAt(int row, int column) {
        Vector rowVector = (Vector)dataVector.elementAt(row);
        return rowVector.elementAt(column);
    }
    
    public void setValueAt(Object aValue, int row, int column) {
        Vector rowVector = (Vector)dataVector.elementAt(row);
        rowVector.setElementAt(aValue, column);
        fireTableCellUpdated(row, column);
    }
	
    public void moveRow(int start, int end, int to) {
        int shift = to - start;
        int first, last;
        if (shift < 0) {
            first = to;
            last = end;
        }
        else {
            first = start;
            last = to + end - start;
        }
        rotate(dataVector, first, last + 1, shift);
        fireTableRowsUpdated(first, last);
    }
    
    private static int gcd(int i, int j) {
        return (j == 0) ? i : gcd(j, i%j);
    }
    
    private static void rotate(Vector v, int a, int b, int shift) {
        int size = b - a;
        int r = size - shift;
        int g = gcd(size, r);
        for(int i = 0; i < g; i++) {
            int to = i;
            Object tmp = v.elementAt(a + to);
            for(int from = (to + r) % size; from != i; from = (to + r) % size) {
                v.setElementAt(v.elementAt(a + from), a + to);
                to = from;
            }
            v.setElementAt(tmp, a + to);
        }
    }
        
	public void newDataAvailable(TableModelEvent event) {
        fireTableChanged(event);
    }
    public void newRowsAdded(TableModelEvent e) {
        fireTableChanged(e);
    }
    public void rowsRemoved(TableModelEvent event) {
        fireTableChanged(event);
    }
	public int getRowCount() {
        return dataVector.size();
    }
	public boolean isCellEditable(int row, int column) {
    	if(column==0||column==1||column==2) return true;
    	else return false;
    }
	private static Vector nonNullVector(Vector v) {
		return (v != null) ? v : new Vector();
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnIdentifiers.size();
	}
	public String getColumnName(int column) {
		return columnIdentifiers.get(column);
	}
}
