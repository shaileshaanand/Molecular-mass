import javax.swing.*;
import java.awt.event.*;
import java.util.*;
public class MolecularMass2 {
    public static void main(String[] args) {
        JFrame f=new JFrame("Molecular Mass");
        f.setSize(340,150);
        f.setLayout(null);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel l1=new JLabel("Compound");
        l1.setBounds(10,10,90,20);
        JTextField tf1=new JTextField();
        tf1.setBounds(90,10,240,20);
        JCheckBox cb=new JCheckBox("Show integer value");
        cb.setBounds(7,70,170,20);
        JTextField tf2=new JTextField();
        tf2.setBounds(90,40,240,20);
        JButton b=new JButton("Calculate");
        b.setBounds(228,70,100,30);
        JLabel l2=new JLabel("Mass");
        l2.setBounds(10,40,90,20);
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int i;
                Entity en;
                MolecularMass2 ob=new MolecularMass2();
                double molecularMass=0;
                try {
                    ArrayList<Entity> ent = ob.getEntities(tf1.getText());
                    for (i=0;i<ent.size();i++) {
                        en=ent.get(i);
                        molecularMass+=en.getMass();
                    }
                    if(cb.isSelected()) {
                        tf2.setText(Long.toString(Math.round(molecularMass)));
                    }else {
                        tf2.setText(Double.toString(molecularMass));
                    }
                }catch(ElementNotFoundException ex) {
                    tf2.setText("error: element "+ex.message()+" not found.");
                }catch (ArrayIndexOutOfBoundsException ex) {
                    tf2.setText("error: invalid compound");
                }
            }
        });
        f.add(l1);
        f.add(l2);
        f.add(tf1);
        f.add(tf2);
        f.add(cb);
        f.add(b);
        f.setVisible(true);
    }
    public ArrayList<Entity> getEntities(String compound) {
        int l=compound.length(),multiplier=1,i,j,ind,arrayIndex=-1;
        char ci,cj;
        String[] entitiest1=new String[30];
        int[] entitiest2=new int[30];
        ArrayList<Entity> entities=new ArrayList<Entity>();
        Entity e;
        for (i=0;i<30;i++) {
            entitiest1[i]="";
            entitiest2[i]=0;
        }
        for (i=0;i<l;i++) {
            ci=compound.charAt(i);
            if(ci=='[') {
                multiplier*=getMultiplier(']',i+1,compound);
                arrayIndex++;
            }else if (ci=='(') {
                multiplier*=getMultiplier(')',i+1,compound);
                arrayIndex++;
            }else if (ci==']' || ci==')') {
                arrayIndex++;
                multiplier/=getMultiplier(ci,i,compound);
            }else {
                if(Character.isUpperCase(ci)) {
                    arrayIndex++;
                }
                entitiest1[arrayIndex]+=ci;
                entitiest2[arrayIndex]=(multiplier!=0)?multiplier:1;
            }
        }
	for(i=0;i<30;i++) {
            if(!entitiest1[i].equals("") && Character.isUpperCase(entitiest1[i].charAt(0))) {
                e=new Entity(entitiest1[i],entitiest2[i]);
                entities.add(e);
            }
	}
        return entities;
    }
    public int getMultiplier(char ch,int i,String S) {
        int multiplier=0,j,ind,x;
        char cj;
        ind=S.indexOf(ch,i);
        for (j=ind+1;j<S.length();j++) {
            cj=S.charAt(j);
            if(Character.isDigit(cj)) {
                multiplier = multiplier*10+Integer.parseInt(Character.toString(cj));
            }else {
                break;
            }
        }
        x=(multiplier!=0)?multiplier:1;
        return x;
    }
}
