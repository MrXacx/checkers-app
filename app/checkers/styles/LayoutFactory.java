package com.calculator.design;

// Classe de componentes
import java.awt.Component;

// Classes de layout
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;

// Classes de vetores
import java.util.Arrays;
import java.util.List;


public class LayoutFactory{
	private final Group sequential_group = layout.createSequentialGroup();
	private final Group  parallel_group = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);		
	
    private Group alignComponents(Group group, List<Component> componentList){	
    	for(Component comp : componentList.toArray(new Component[0]))
    		group = group.addComponent(comp, this.SIZE, this.SIZE, this.SIZE);

    	return group;
    }

    public void setHorizontalGeneralGroup(GroupLayout layout){
    	layout.setHorizontalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(this.alignComponents(sequential_group, Arrays.asList()))                          
					.addGroup(this.alignComponents(sequential_group, Arrays.asList()))
					.addGroup(this.alignComponents(sequential_group, Arrays.asList()))
					.addGroup(this.alignComponents(sequential_group, Arrays.asList()))              
					.addGroup(this.alignComponents(sequential_group, Arrays.asList()))
					.addGroup(this.alignComponents(sequential_group, Arrays.asList()))
					.addGroup(this.alignComponents(sequential_group, Arrays.asList()))
					.addGroup(this.alignComponents(sequential_group, Arrays.asList()))
			);
    }
    
    public void setVerticalGeneralGroup(GroupLayout layout){
    	layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(sequential_group
						.addGroup(this.alignComponents(, Arrays.asList()))       
						.addGroup(this.alignComponents(parallel_group, Arrays.asList()))
              		.addGroup(this.alignComponents(parallel_group, Arrays.asList()))
              		.addGroup(this.alignComponents(parallel_group, Arrays.asList()))
						.addGroup(this.alignComponents(parallel_group, Arrays.asList()))
						.addGroup(this.alignComponents(parallel_group, Arrays.asList()))
              		.addGroup(this.alignComponents(parallel_group, Arrays.asList()))
              		.addGroup(this.alignComponents(parallel_group, Arrays.asList()))
              		.addGroup(this.alignComponents(parallel_group, Arrays.asList()))
		));
	}	
}
