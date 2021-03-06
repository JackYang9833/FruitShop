package site.luvzy.view;
import site.luvzy.db.DBOperation;
import site.luvzy.tools.GuiTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
/**
 * @项目名称 luvzy
 * @日期 2021/6/16
 */
public abstract class MyDialog extends JDialog {
    private final JLabel tableLabel = new JLabel("水果列表");
    private JScrollPane scrollTable = new JScrollPane();
    protected JTable table = new JTable();
    /**
     * 输入框返回的数据
     */
    private int number;//
    private int price;
    private String name;
    private String unit;
    /**
     * 标签
     */
    String []labelName = new String[]{"编号", "名称", "单价", "单位"};
    JLabel []label = new JLabel[4];
    /**
     * 按钮
     */
    private final JButton []button = new JButton[4];
    /**
     * 文本框
     */
    JTextField []editAdd = new JTextField[4];//添加的文本框
    JTextField []editModify = new JTextField[4];//修改内容的文本框
    JTextField editQuery = new JTextField(6);//搜索
    JTextField editDelete = new JTextField(6);//删除
    //构造方法
    public MyDialog() {
        this(null, true);
    }

    public MyDialog(Frame owner, Boolean modal) {
        super(owner, modal);
        init();
        addComponent();
        addFruitItem();
    }
    //初始化
    private void init() {
        this.setTitle("超市货物管理");
        this.setSize(600, 500);
        GuiTools.center(this);
        this.setResizable(false);
        onResume();
    }

    private void addComponent() {
        this.setLayout(null);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        //table.enable(false);
        tableLabel.setBounds(265, 20, 70, 25);
        scrollTable.setBounds(50, 50, 500, 200);
        scrollTable.setViewportView(table);
        this.add(tableLabel);
        this.add(scrollTable);

        int x = 50;
        button[0] = new JButton("添加水果");
        button[1] = new JButton("修改水果");
        button[2] = new JButton("删除水果");
        button[3] = new JButton("查找水果");

        for (int i = 0; i < 4; ++i) {
            label[i] = new JLabel(labelName[i]);
            label[i].setBounds(x, 250, 70, 25);
            this.add(label[i]);//标签
            // add
            editAdd[i] = new JTextField(6);
            editAdd[i].setBounds(x, 280, 80, 25);
            this.add(editAdd[i]);
            x = x + 100;
        }
        button[0].setBounds(460, 280, 90, 25);
        this.add(button[0]);
        x = 50;
        //modi
        for (int i = 0; i < 4; ++i) {
            editModify[i] = new JTextField(6);
            editModify[i].setBounds(x, 310, 80, 25);
            this.add(editModify[i]);
            x += 100;
        }
        button[1].setBounds(460, 310, 90, 25);
        this.add(button[1]);
        //delete
        editDelete.setBounds(50, 340, 80, 25);
        this.add(editDelete);
        button[2].setBounds(460, 340, 90, 25);
        this.add(button[2]);
        //query
        editQuery.setBounds(50, 370, 80, 25);
        this.add(editQuery);
        button[3].setBounds(460, 370, 90, 25);
        this.add(button[3]);

        button[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFruitItem();


                /**
                 * for test
                 */
                number = Integer.parseInt(editAdd[0].getText());
                name = editAdd[1].getText();
                price = Integer.parseInt(editAdd[2].getText());
                unit = editAdd[3].getText();
                       /* number=1;
                        name ="凤梨";
                        price =12;
                        unit ="单位";*/
                try {
                    DBOperation operation = new DBOperation();
                    operation.addData(number,name,price,unit);
                    System.out.println("Insert finish!");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                System.out.println(number);
                System.out.println(name);
                System.out.println(price);
                System.out.println(unit);
            }
        });

        button[1].addActionListener(e -> modifyFruitItem());

        button[2].addActionListener(e -> deleteFruitItem());

        button[3].addActionListener(e -> queryFruitItem());
    }

    public abstract void onResume();//显示

    public abstract void addFruitItem();

    public abstract void queryFruitItem();

    public abstract void deleteFruitItem();

    public abstract void modifyFruitItem();
}
