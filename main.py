from flask import Flask, jsonify,request
from db.createTableOperation import createTable,product_table,user_order,sell_history_table,User_Stock
from db.addOperation import CreateUser,user_auth,add_order,add_product,sell_history_detail,add_Product_User_Stock
from db.readOperation import getAllUser,get_product_by_user_id_from_user_stock,getSpecificUser,get_product,getSpecificProduct,user_get_order_detail,fetch_order_details_by_filter,get_sell_history,get_sell_history_by_user_id,get_product_from_user_stock
from db.updateOperation import update_user_detail,update_admin_product,update_order_detail
from db.deleteOperation import delete_specific_user,delete_all_user
app = Flask(__name__)

@app.route("/", methods=['GET'])
def hello():
    return jsonify({"message": "Hello, World!"})

#Create A New User (Registration)

@app.route("/createUser",methods=['POST'])
def createUser():
    try:
        # Collect form data
        name = request.form['name']
        password = request.form['password']
        email = request.form['email']
        pincode = request.form['pincode']
        phone_number = request.form['phone_number']
        address = request.form['address']

        # Call createUser and pass the collected data
        data = CreateUser(name=name, password=password, phone_number=phone_number, email=email, pincode=pincode, address=address)

        # Return the response using jsonify
        return jsonify({"message": "User created successfully", "user": data}), 201
    
    except Exception as e:
        # Return error message in case of any issues
        return jsonify({"error": str(e)}), 500
    
#User Login

@app.route("/login", methods=['POST'])
def login():
    
    email = request.form['email']
    password = request.form['password']

    # Call the user_auth function to authenticate
    result = user_auth(email=email,password=password)

    # Return the result as a JSON response
    if result:
        return jsonify({"message":result[1],"status": 200})
    else:
        return jsonify({"message": "null"})
    
#Get All User Details 

@app.route("/getAllUsers", methods=['GET'])
def getAllUsers():
    return getAllUser()

#Get Specific User Details

@app.route('/getSpecificUser_data', methods=['POST'])
def getSpecificUser_data():
    try:
        user_id = request.form['user_id']
        data = getSpecificUser(user_id=user_id)
        return jsonify({"message": "User data fetched successfully", "user": data}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500


#Update User Details 

   
@app.route('/update_userDetail', methods=['PATCH'])
def update_userDetails():
    try:
        # Check if JSON is being sent, otherwise fallback to form data
        if request.is_json:
            data = request.get_json()
        else:
            data = request.form

        # Get the user_id from the request
        user_id = data.get('user_id')

        if not user_id:
            return jsonify({"status": 400, "message": "user_id is required"}), 400

        # Prepare the update fields, excluding 'user_id'
        updateUser = {key: value for key, value in data.items() if key != 'user_id'}
        
        if not updateUser:
            return jsonify({"status": 400, "message": "No fields to update"}), 400

        # Call the update function
        update_user_detail(user_id=user_id, **updateUser)

        # Return a success message
        return jsonify({"status": 200, "message": "Update successful"})

    except Exception as e:
        return jsonify({"status": 400, "message": str(e)}), 400

    
# Delete Specific User

@app.route("/deleteSpecificUser",methods=["DELETE"])
def deleteSpecificUser():
    try:
        user_id = request.form['user_id']
        delete_specific_user(user_id=user_id)
        return jsonify({"message": "User deleted successfully"}), 200
    except Exception as error:
        return jsonify({"error": str(error)}), 500

#Delete All User
@app.route("/deleteAllUser",methods=["DELETE"])
def deleteAllUser():
    try:
        delete_all_user()
        return jsonify({"message": "All Users deleted successfully"}), 200
    except Exception as error:
        return jsonify({"error": str(error)}), 500

#Add Product 

@app.route("/add_product", methods=['POST'])
def add_product_route():
    
    product_name = request.form['product_name']
    stock = request.form['stock']
    price = request.form['price']
    category = request.form['category']
    #expire_date = request.form['expire_date']

    
    product_id = add_product(product_name, stock, price, category)

    if product_id:
        
        return jsonify({"message": "Product creation successful", "product_id": product_id}), 201
    else:
        
        return jsonify({"message": "Failed to create product"}), 500
    
#Get ALLProduct 


@app.route('/get_all_product', methods=['GET'])
def get_all_product():
    return get_product()

# Get Specific Product 
@app.route('/getSpecificProduct', methods=['POST'])
def getSpecific_Product():
    try:
        product_id = request.form['product_id']
        data = getSpecificProduct(product_id=product_id)
        return jsonify({"message": "Product detail  fetched successfully", "user": data}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500


# Update Admin Product 

@app.route('/update_adminProduct', methods=['PATCH'])
def update_adminProduct():
    try:
        # Check if JSON is being sent
        if request.is_json:
            data = request.get_json()
        else:
            # Fallback to form data if not JSON
            data = request.form
        
        # Get the product_id from the request
        product_id = data.get('product_id')
        
        if not product_id:
            return jsonify({"status": 400, "message": "product_id is required"}), 400
        
        # Prepare the update fields, excluding 'product_id'
        updateProduct = {key: value for key, value in data.items() if key != 'product_id'}
        
        if not updateProduct:
            return jsonify({"status": 400, "message": "No fields to update"}), 400
        
        # Call the update function
        result = update_admin_product(product_id=product_id, **updateProduct)

        return jsonify({"status": 200, "message": "Update successful"})

    except Exception as e:
        return jsonify({"status": 400, "message": str(e)}), 400
    
#Add Oder Details


@app.route('/user_addStock', methods=['POST'])
def user_addStock():
    try:
        user_id = request.form['user_id']
        product_id = request.form['product_id']
        quantity = request.form['quantity']
        price = request.form['price']
        total_amount = request.form['total_amount']
        product_name = request.form['product_name']
        user_name = request.form['user_name']  # Fixed the extra space
        message = request.form['message']
        category = request.form['category']

        # Call the add_order function with the retrieved form data
        order = add_order(user_id=user_id, product_id=product_id, 
                          quantity=quantity, price=price, 
                          total_amount=total_amount, product_name=product_name, 
                          user_name=user_name, message=message, category=category)

        return jsonify({"status": 200, "message": "Order added successfully", "order": order})
    except Exception as e:
        return jsonify({"status": 400, "message": str(e)}), 400

#Get All User Order Detail 
@app.route("/get_order_detail", methods=['GET'])
def get_order_detail():
    return user_get_order_detail()


#Update Order Details from flask import Flask, request, jsonify
"""
@app.route('/updateUserOrderTable', methods=['PATCH'])
def updateUserOrderTable():
    try:
        # Parse form data
        data = request.form
        
        # Get the order_id from the request
        order_id = data.get('order_id')
        if not order_id:
            return jsonify({"status": 400, "message": "order_id is required"}), 400

        # Prepare fields to update, excluding 'order_id'
        updateOrder = {key: value for key, value in data.items() if key != 'order_id'}

        if not updateOrder:
            return jsonify({"status": 400, "message": "No fields to update"}), 400

        # Call the update function
        result = update_order_detail(order_id=order_id, **updateOrder)

        # Check for errors in the update function response
        if "error" in result:
            return jsonify({"status": 400, "message": result["error"]}), 400

        # Success response
        return jsonify({"status": 200, "message": result["message"]}), 200

    except Exception as e:
        return jsonify({"status": 500, "message": str(e)}), 500

"""

  
@app.route('/updateUserOrderTable', methods=['PATCH'])
def updateUserOrderTable():
    try:
        # Check if JSON is being sent, otherwise fallback to form data
        if request.is_json:
            data = request.get_json()
        else:
            data = request.form

        # Get the user_id from the request
        order_id = data.get('order_id')

        if not order_id:
            return jsonify({"status": 400, "message": "user_id is required"}), 400

        # Prepare the update fields, excluding 'user_id'
        updateOrder = {key: value for key, value in data.items() if key != 'order_id'}
        
        if not updateOrder:
            return jsonify({"status": 400, "message": "No fields to update"}), 400

        # Call the update function
        update_order_detail(order_id=order_id, **updateOrder)

        # Return a success message
        return jsonify({"status": 200, "message": "Update successful"})

    except Exception as e:
        return jsonify({"status": 400, "message": str(e)}), 400

# Get Order Details by filter 


@app.route('/getOrderDetailsByFilter', methods=['POST'])
def getOrderDetailsByFilter():
    try:
        # Parse form data
        user_id = request.form.get('user_id')
        isApproved = request.form.get('isApproved')

        # Validate input parameters
        if not user_id:
            return jsonify({"status": 400, "message": "user_id is required"}), 400
        if isApproved is None:  # Allow `isApproved` to be 0 or False
            return jsonify({"status": 400, "message": "isApproved is required"}), 400

        # Fetch data using utility function
        result = fetch_order_details_by_filter(user_id, isApproved)

        # Check if an error occurred
        if isinstance(result, dict) and "error" in result:
            return jsonify({"status": 500, "message": result["error"]}), 500

        # Return fetched order details or a 404 if none found
        return jsonify(result), 200 if result.get("status") == 200 else 404

    except Exception as e:
        return jsonify({"status": 500, "message": str(e)}), 500

# sell History 
@app.route("/sellHistory", methods=['POST'])
def sellHistory():
    try:
        # Collect form data
        product_id = request.form['product_id']
        quantity = int(request.form['quantity'])  # Convert to integer
        remaining_stock = int(request.form['remaining_stock'])  # Convert to integer
        price = float(request.form['price'])  # Convert to float
        total_amount = float(request.form['total_amount'])  # Convert to float
        user_id = request.form['user_id']
        product_name = request.form['product_name']
        user_name = request.form['user_name']
        date_of_sale = request.form['date_of_sale'] # Use provided date or default to today

        # Call sell_history_detail and pass the collected data
        data = sell_history_detail(
            product_id=product_id,
            quantity=quantity,
            remaining_stock=remaining_stock,
            date_of_sale=date_of_sale,
            price=price,
            total_amount=total_amount,
            product_name=product_name,
            user_name=user_name,
            user_id=user_id
        )

        # Check for errors in the response
        if "error" in data:
            return jsonify({"error": data["error"]}), 500

        # Return success response
        return jsonify({"message": "Sale record created successfully", "data": data}), 201

    except Exception as e:
        # Return error message in case of any issues
        return jsonify({"error": str(e)}), 500

# get Sell History


@app.route("/getSellHistory", methods=['GET'])
def getSellHistory():
    return get_sell_history()

# get Sell History by user id
@app.route('/getSellHistoryByUserId', methods=['POST'])
def getSellHistoryByUserId():
    """
    API endpoint to fetch sell history details by user ID.
    """
    try:
        # Extract user_id from the form data
        user_id = request.form.get('user_id')

        # Validate input
        if not user_id:
            return jsonify({"status": 400, "message": "user_id is required"}), 400

        # Fetch the sell history data
        data = get_sell_history_by_user_id(user_id=user_id)

        # Handle cases where no records are found
        if isinstance(data, dict) and "error" in data:
            return jsonify({"status": 500, "message": data["error"]}), 500
        elif not data:  # Empty result
            return jsonify({"status": 404, "message": "No sell history found for the given user_id"}), 404

        # Return success response with data
        return jsonify({"status": 200, "message": "Sell history details fetched successfully", "sell_history": data}), 200

    except Exception as e:
        # Return error response in case of exceptions
        return jsonify({"status": 500, "message": str(e)}), 500

# Add to Product (User STock )
@app.route("/addToProductInUserStock", methods=['POST'])
def addToProductInUserStock():
    try:
        # Collect form data from the request
        user_id = request.form['user_id']
        product_name = request.form['product_name']
        user_name = request.form['user_name']
        stock = request.form['stock']
        price = request.form['price']
        category = request.form['category']

        # Call the add_Product_User_Stock function with the collected data
        data = add_Product_User_Stock(
            user_id=user_id,
            product_name=product_name,
            user_name=user_name,
            stock=stock,
            price=price,
            category=category
        )

        # Return the response using jsonify
        return jsonify({"message": "Product added successfully", "user": data}), 201
    
    except Exception as e:
        # Return error message in case of any issues
        return jsonify({"error": str(e)}), 500


# get Product (User Stock)

@app.route("/getProductFromUserStock", methods=['GET'])
def getProductFromUserStock():
    return get_product_from_user_stock()
    

# get Product By user Id (User Stock)
@app.route('/getProductUserStockByUserId', methods=['POST'])
def getProductUserStockByUserId():
    """
    API endpoint to fetch sell history details by user ID.
    """
    try:
        # Extract user_id from the form data
        user_id = request.form.get('user_id')

        # Validate input
        if not user_id:
            return jsonify({"status": 400, "message": "user_id is required"}), 400

        # Fetch the sell history data
        data = get_product_by_user_id_from_user_stock(user_id=user_id)

        # Handle cases where no records are found
        if isinstance(data, dict) and "error" in data:
            return jsonify({"status": 500, "message": data["error"]}), 500
        elif not data:  # Empty result
            return jsonify({"status": 404, "message": "Not found for the given user_id"}), 404

        # Return success response with data
        return jsonify({"status": 200, "message": "Product  details fetched successfully", "sell_history": data}), 200

    except Exception as e:
        # Return error response in case of exceptions
        return jsonify({"status": 500, "message": str(e)}), 500

if __name__ == "__main__":
    app.run(debug=True)
    createTable()
    product_table()
    user_order()
    sell_history_table()
    User_Stock()
    