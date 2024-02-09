import java.awt.*;
import java.awt.event.*;

public class Java_Calculator_Project extends Frame {

    // Variable to track whether to clear the display after button press
    public boolean setClearButton = true;
    // Variables to store current calculation state
    double number_entry, memValue;
    char op;
    
    /*Visible button declaration*/    
    String inputButtonText[] = {"7","8","9","4","5","6","1","2","3","0","+/-","."};
    String operatorButtonsText[] = {"/", "sqrt", "*","%","-","1/X","+","="};
    String memoryButtonsText[] = {"MC","MR","MS","M+"};
    String correctionButtonsText[] = {"Backspc","C","CE"};

    // Arrays to store instances of different types of buttons
    MyInputButton inputButton[] = new MyInputButton[inputButtonText.length];
    MyOperatorButtons operatorButtons[] = new MyOperatorButtons[operatorButtonsText.length];
    MyMemoryButtons memoryButtons[] = new MyMemoryButtons[memoryButtonsText.length];
    MyCorrectionButtons correctionButtons[] = new MyCorrectionButtons[correctionButtonsText.length];

    // Labels to display the current value and memory value
    Label showLabel = new Label("0", Label.RIGHT);
    Label memLabel = new Label("0", Label.RIGHT);


    final int FRAME_WIDTH = 325, FRAME_HEIGHT = 325;
    final int HEIGHT = 30, WIDTH = 30, H_SPACE = 10, V_SPACE = 10;
    final int TOPX = 30, TOPY = 50;

    Java_Calculator_Project(String frameText) {//constructor
        super(frameText);

        // Setting up the display labels
        int tempX = TOPX, y = TOPY;
        showLabel.setBounds(tempX, y, 240, HEIGHT);
        showLabel.setBackground(Color.GRAY);
        showLabel.setForeground(Color.WHITE);
        add(showLabel);

        memLabel.setBounds(TOPX, TOPY + HEIGHT + V_SPACE, WIDTH, HEIGHT);
        add(memLabel);

        // Creating Memory Buttons
        tempX = TOPX;
        y = TOPY + 2 * (HEIGHT + V_SPACE);
        for (int i = 0; i < memoryButtons.length; i++) {
            memoryButtons[i] = new MyMemoryButtons(tempX, y, WIDTH, HEIGHT, memoryButtonsText[i], this);
            memoryButtons[i].setBackground(Color.LIGHT_GRAY);
            memoryButtons[i].setForeground(Color.DARK_GRAY);
            y += HEIGHT + V_SPACE;
        }

        // Creating Correction Buttons
        tempX = TOPX + 1 * (WIDTH + H_SPACE);
        y = TOPY + 1 * (HEIGHT + V_SPACE);
        for (int i = 0; i < correctionButtons.length; i++) {
            correctionButtons[i] = new MyCorrectionButtons(tempX, y, WIDTH * 2, HEIGHT, correctionButtonsText[i], this);
            correctionButtons[i].setBackground(Color.LIGHT_GRAY);
            correctionButtons[i].setForeground(Color.DARK_GRAY);
            tempX = tempX + 2 * WIDTH + H_SPACE;
        }

        // Creating Input Buttons
        int digitX = TOPX + WIDTH + H_SPACE;
        int digitY = TOPY + 2 * (HEIGHT + V_SPACE);
        tempX = digitX;
        y = digitY;
        for (int i = 0; i < inputButton.length; i++) {
            inputButton[i] = new MyInputButton(tempX, y, WIDTH, HEIGHT, inputButtonText[i], this);
            inputButton[i].setBackground(Color.LIGHT_GRAY);
            inputButton[i].setForeground(Color.DARK_GRAY);
            tempX += WIDTH + H_SPACE;
            if ((i + 1) % 3 == 0) {
                tempX = digitX;
                y += HEIGHT + V_SPACE;
            }
        }

        // Creating Operator Buttons
        int operationsX = digitX + 2 * (WIDTH + H_SPACE) + H_SPACE;
        int operationsY = digitY;
        tempX = operationsX;
        y = operationsY;
        for (int i = 0; i < operatorButtons.length; i++) {
            tempX += WIDTH + H_SPACE;
            operatorButtons[i] = new MyOperatorButtons(tempX, y, WIDTH, HEIGHT, operatorButtonsText[i], this);
            operatorButtons[i].setBackground(Color.LIGHT_GRAY);
            operatorButtons[i].setForeground(Color.DARK_GRAY);
            if ((i + 1) % 2 == 0) {
                tempX = operationsX;
                y += HEIGHT + V_SPACE;
            }
        }

        // Opens window for calculator Showcase
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });

        setLayout(null);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
    }

    // Utility function to format the output text
    static String getFormattedText(double temp) {
        String resetText = "" + temp;
        if (resetText.lastIndexOf(".0") > 0)
            resetText = resetText.substring(0, resetText.length() - 2);
        return resetText;
    }

    public static void main(String[] args) {
        new Java_Calculator_Project("Calculator - JerBear_calculator");
    }
}

// Class for input buttons regarding the numbers themselves
class MyInputButton extends Button implements ActionListener {
    Java_Calculator_Project calculator_name;

    MyInputButton(int x, int y, int width, int height, String cap, Java_Calculator_Project clc) {
        super(cap);
        setBounds(x, y, width, height);
        this.calculator_name = clc;
        this.calculator_name.add(this);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent ev) {
        // Method to handle input button clicks
        String tempText = ((MyInputButton) ev.getSource()).getLabel();

        if (tempText.equals(".")) {
            if (calculator_name.setClearButton) {
                calculator_name.showLabel.setText("0.");
                calculator_name.setClearButton = false;
            } else if (!isInString(calculator_name.showLabel.getText(), '.')) {
                calculator_name.showLabel.setText(calculator_name.showLabel.getText() + ".");
            }
            return;
        }
        int index = 0;
        try {
            index = Integer.parseInt(tempText);
        } catch (NumberFormatException e) {
            return;
        }
        if (index == 0 && calculator_name.showLabel.getText().equals("0")) return;

        if (calculator_name.setClearButton) {
            calculator_name.showLabel.setText("" + index);
            calculator_name.setClearButton = false;
        } else
            calculator_name.showLabel.setText(calculator_name.showLabel.getText() + index);
    }

    static boolean isInString(String s, char ch) {
        // Method to check if a character is present in a string
        for (int i = 0; i < s.length(); i++) if (s.charAt(i) == ch) return true;
        return false;
    }
}

// Class for operator buttons
class MyOperatorButtons extends Button implements ActionListener {
    Java_Calculator_Project calculator_name;

    MyOperatorButtons(int x, int y, int width, int height, String cap, Java_Calculator_Project clc) {
        super(cap);
        setBounds(x, y, width, height);
        this.calculator_name = clc;
        this.calculator_name.add(this);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent ev) {
        // Method to handle operator button clicks
        String operationText = ((MyOperatorButtons) ev.getSource()).getLabel();
        calculator_name.setClearButton = true;
        double temp = Double.parseDouble(calculator_name.showLabel.getText());

        if (operationText.equals("1/X")) {
            try {
                double tempd = 1 / temp;
                calculator_name.showLabel.setText(Java_Calculator_Project.getFormattedText(tempd));
            } catch (ArithmeticException excp) {
                calculator_name.showLabel.setText("Divide by 0.");
            }
            return;
        }
        if (!operationText.equals("=")) {
            calculator_name.number_entry = temp;
            calculator_name.op = operationText.charAt(0);
            return;
        }
        // this captures the button press for any input
        switch (calculator_name.op) {
            case '+':
                temp += calculator_name.number_entry;
                break;
            case '-':
                temp = calculator_name.number_entry;
                break;
            case '*':
                temp *= calculator_name.number_entry;
                break;
            case '%':
                try {
                    temp = calculator_name.number_entry % temp;
                } catch (ArithmeticException excp) {
                    calculator_name.showLabel.setText("Divide by 0.");
                    return;
                }
                break;
            case '/':
                try {
                    temp = calculator_name.number_entry / temp;
                } catch (ArithmeticException excp) {
                    calculator_name.showLabel.setText("Divide by 0");
                    return;
                }
                break;
        }
        calculator_name.showLabel.setText(Java_Calculator_Project.getFormattedText(temp));
    }
}

// Class for memory buttons
class MyMemoryButtons extends Button implements ActionListener {
    Java_Calculator_Project calculator_name;

    MyMemoryButtons(int x, int y, int width, int height, String cap, Java_Calculator_Project clc) {
        super(cap);
        setBounds(x, y, width, height);
        this.calculator_name = clc;
        this.calculator_name.add(this);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent ev) {
        // Method to handle memory button clicks
        char memory_operation = ((MyMemoryButtons) ev.getSource()).getLabel().charAt(1);
        calculator_name.setClearButton = true;
        double temp = Double.parseDouble(calculator_name.showLabel.getText());

        switch (memory_operation) {
            case 'C':
                calculator_name.memLabel.setText(" ");
                calculator_name.memValue = 0.0;
                break;
            case 'R':
                calculator_name.showLabel.setText(Java_Calculator_Project.getFormattedText(calculator_name.memValue));
                break;
            case 'S':
                calculator_name.memValue = 0.0;
            case '+':
                calculator_name.memValue += Double.parseDouble(calculator_name.showLabel.getText());
                if (calculator_name.showLabel.getText().equals("0") || calculator_name.showLabel.getText().equals("0.0"))
                    calculator_name.memLabel.setText(" ");
                else
                    calculator_name.memLabel.setText("M");
                break;
        }
    }
}

// Class for correction buttons
class MyCorrectionButtons extends Button implements ActionListener {
    Java_Calculator_Project calculator_name;

    MyCorrectionButtons(int x, int y, int width, int height, String cap, Java_Calculator_Project clc) {
        super(cap);
        setBounds(x, y, width, height);
        this.calculator_name = clc;
        this.calculator_name.add(this);
        addActionListener(this);
    }

    static String backSpace(String s) {
        // Method to simulate backspace
        String Reset = "";
        for (int i = 0; i < s.length() - 1; i++) Reset += s.charAt(i);
        return Reset;
    }

    public void actionPerformed(ActionEvent ev) {
        // Method to handle correction button clicks
        String operationText = ((MyCorrectionButtons) ev.getSource()).getLabel();
        // reads and determines if the backspace has been pressed
        if (operationText.equals("Backspc")) {
            String tempText = backSpace(calculator_name.showLabel.getText());
            if (tempText.equals(""))
                calculator_name.showLabel.setText("0");
            else
                calculator_name.showLabel.setText(tempText);
            return;
        }

        // Checking if the C or clear button is pressed thus a reset
        if (operationText.equals("C")) {
            calculator_name.number_entry = 0.0;
            calculator_name.op = '\0';
            calculator_name.memValue = 0.0;
            calculator_name.memLabel.setText(" ");
        }

        // The CE button pressed
        calculator_name.showLabel.setText("0");
        calculator_name.setClearButton = true;
    }
}
