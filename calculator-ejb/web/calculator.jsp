<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EJB Calculator</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        
        .container {
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            padding: 40px;
            max-width: 500px;
            width: 100%;
        }
        
        h1 {
            color: #667eea;
            text-align: center;
            margin-bottom: 10px;
            font-size: 2em;
        }
        
        .subtitle {
            text-align: center;
            color: #666;
            margin-bottom: 30px;
            font-size: 0.9em;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
        }
        
        input[type="text"] {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 10px;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        
        input[type="text"]:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .operation-buttons {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 10px;
            margin-bottom: 20px;
        }
        
        .operation-btn {
            padding: 15px;
            border: 2px solid #667eea;
            background: white;
            color: #667eea;
            border-radius: 10px;
            font-size: 18px;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.3s;
        }
        
        .operation-btn:hover {
            background: #667eea;
            color: white;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.3);
        }
        
        .operation-btn.selected {
            background: #667eea;
            color: white;
        }
        
        .submit-btn {
            width: 100%;
            padding: 15px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 18px;
            font-weight: bold;
            cursor: pointer;
            transition: transform 0.3s;
        }
        
        .submit-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(102, 126, 234, 0.4);
        }
        
        .result {
            margin-top: 25px;
            padding: 20px;
            background: linear-gradient(135deg, #667eea15 0%, #764ba215 100%);
            border-radius: 10px;
            text-align: center;
            font-size: 1.3em;
            font-weight: bold;
            color: #667eea;
        }
        
        .error {
            margin-top: 25px;
            padding: 20px;
            background: #ffebee;
            border-radius: 10px;
            text-align: center;
            color: #c62828;
            font-weight: 500;
        }
        
        .footer {
            margin-top: 30px;
            text-align: center;
            color: #999;
            font-size: 0.85em;
        }
        
        input[type="radio"] {
            display: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🧮 EJB Calculator</h1>
        <p class="subtitle">Powered by J2EE Session Beans</p>
        
        <form method="post" action="calculator">
            <div class="form-group">
                <label for="number1">First Number:</label>
                <input type="text" id="number1" name="number1" 
                       value="<%= request.getAttribute("number1") != null ? request.getAttribute("number1") : "" %>"
                       placeholder="Enter first number" required>
            </div>
            
            <div class="form-group">
                <label for="number2">Second Number:</label>
                <input type="text" id="number2" name="number2" 
                       value="<%= request.getAttribute("number2") != null ? request.getAttribute("number2") : "" %>"
                       placeholder="Enter second number" required>
            </div>
            
            <div class="form-group">
                <label>Select Operation:</label>
                <div class="operation-buttons">
                    <%
                        String selectedOp = (String) request.getAttribute("operation");
                    %>
                    <input type="radio" id="add" name="operation" value="add" 
                           <%= "add".equals(selectedOp) ? "checked" : "" %> required>
                    <label for="add" class="operation-btn <%= "add".equals(selectedOp) ? "selected" : "" %>">
                        + Add
                    </label>
                    
                    <input type="radio" id="subtract" name="operation" value="subtract"
                           <%= "subtract".equals(selectedOp) ? "checked" : "" %> required>
                    <label for="subtract" class="operation-btn <%= "subtract".equals(selectedOp) ? "selected" : "" %>">
                        - Subtract
                    </label>
                    
                    <input type="radio" id="multiply" name="operation" value="multiply"
                           <%= "multiply".equals(selectedOp) ? "checked" : "" %> required>
                    <label for="multiply" class="operation-btn <%= "multiply".equals(selectedOp) ? "selected" : "" %>">
                        × Multiply
                    </label>
                    
                    <input type="radio" id="divide" name="operation" value="divide"
                           <%= "divide".equals(selectedOp) ? "checked" : "" %> required>
                    <label for="divide" class="operation-btn <%= "divide".equals(selectedOp) ? "selected" : "" %>">
                        ÷ Divide
                    </label>
                </div>
            </div>
            
            <button type="submit" class="submit-btn">Calculate</button>
        </form>
        
        <%
            String result = (String) request.getAttribute("result");
            String error = (String) request.getAttribute("error");
            
            if (result != null && !result.isEmpty()) {
        %>
            <div class="result">
                <%= result %>
            </div>
        <%
            }
            
            if (error != null && !error.isEmpty()) {
        %>
            <div class="error">
                <%= error %>
            </div>
        <%
            }
        %>
        
        <div class="footer">
            Classic J2EE • EJB 2.x • JBoss Application Server
        </div>
    </div>
    
    <script>
        // Add interactivity to operation buttons
        document.querySelectorAll('.operation-btn').forEach(function(btn) {
            btn.addEventListener('click', function() {
                document.querySelectorAll('.operation-btn').forEach(function(b) {
                    b.classList.remove('selected');
                });
                this.classList.add('selected');
                document.getElementById(this.getAttribute('for')).checked = true;
            });
        });
    </script>
</body>
</html>
