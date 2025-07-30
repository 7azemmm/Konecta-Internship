// global variables
let products = [];
let cart = [];
let discountCodes = ['apply10' , 'apply25']
let appliedDiscount = 0; 


// DOM elements
const productsGrid = document.getElementById('productsGrid');
const cartCount = document.getElementById('cartCount');
const cartEmpty = document.getElementById('cartEmpty');
const cartItems = document.getElementById('cartItems');
const cartFooter = document.getElementById('cartFooter');
const cartTotal = document.getElementById('cartTotal');
const confirmOrderBtn = document.getElementById('confirmOrderBtn');
const startNewOrderBtn = document.getElementById('startNewOrderBtn');
const orderPopup = document.getElementById('orderPopup');
const orderPopupOverlay = document.getElementById('orderPopupOverlay');
const orderDiscountBtn = document.getElementById('discountbtn');
const orderDiscount = document.getElementById('discountCode');
const printPdf = document.getElementById('printPdfBtn');
const discountCodeStatus = document.getElementById('DiscoundCodeStatus')

//eventlistener
confirmOrderBtn.addEventListener('click', confirmOrder);
startNewOrderBtn.addEventListener('click', startNewOrder);
orderPopupOverlay.addEventListener('click', closeorderPopup);
orderDiscountBtn.addEventListener('click' , checkDiscount);
printPdf.addEventListener('click', generateCartPDF);


document.addEventListener('DOMContentLoaded', () => {
  init();
  
});

async function init() {
  try {
    await loadProducts();
    renderProducts();
  
  } catch (error) {
    console.error('Error initializing app:', error);
  }
}



// Load products from data.json
async function loadProducts() {
  try {
    const response = await fetch('./data.json');
    products = await response.json();
    console.log(products)
  } catch (error) {
    console.log('Error in loading products:', error);
    throw error;
  }
}


function renderProducts() {
  productsGrid.innerHTML = products.map(product => {
    const cartItem = cart.find(item => item.name === product.name);
    const quantity = cartItem ? cartItem.quantity : 0;
    
    return `
      <article class="product-card">
        <img 
          src="${product.image.desktop}" 
          alt="${product.name}"
          class="product-card-image"
          loading="lazy"
        >
        <div class="product-card-content">
          <h3 class="product-card-name">${product.name}</h3>
          <p class="product-card-category">${product.category}</p>
          <p class="product-card-price">$${product.price.toFixed(2)}</p>
        
              <button 
                class="product-card-button"
                onclick="addToCart('${product.name}')"
              >
                <img src="./assets/images/add-to-cart.png" alt="add-to-cart">
                Add to Cart
              </button>
        </div>
      </article>
    `;
  }).join('');
}

function addToCart(productName) {
  const product = products.find(p => p.name === productName);
  if (!product) return;

  const ExistingItem = cart.find(item => item.name === productName);
  
  if (ExistingItem) {
    ExistingItem.quantity += 1;
  } else {
    cart.push({
      ...product,
      quantity: 1
    });
  }

  updateCartDisplay();
  
}


function RemoveFromCart(productName) {
  cart = cart.filter(item => item.name !== productName);
  updateCartDisplay();
}


function updateQuantity(productName, change) {
  const item = cart.find(item => item.name === productName);
  if (!item) return;

  item.quantity += change;
  
  if (item.quantity <= 0) {
    RemoveFromCart(productName);
  } else {
    updateCartDisplay();
  }
}


function updateCartDisplay(discountPercentage = appliedDiscount) {
  var totalItems = cart.reduce((sum, item) => sum + item.quantity, 0);
  var totalPrice = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
   let discountMoney = 0;
  if (discountPercentage) {
    discountMoney = totalPrice * discountPercentage;
    totalPrice -= discountMoney;
  }

  
  cartCount.textContent = `(${totalItems})`;
   
  if (cart.length === 0) {
    cartEmpty.style.display = 'block';
    cartItems.style.display = 'none';
    cartFooter.style.display = 'none';
  } else {
    cartEmpty.style.display = 'none';
    cartItems.style.display = 'block';
    cartFooter.style.display = 'block';
  }

  // Render cart items
  cartItems.innerHTML = cart.map(item => `
    <div class="cart-item">
      <img 
        src="${item.image.thumbnail}" 
        alt="${item.name}"
        class="cart-item-image"
      >
      <div class="cart-item-details">
        <h4 class="cart-item-name">${item.name}</h4>
        <p class="cart-item-price">$${item.price.toFixed(2)}</p>
      </div>
      <div class="cart-item-controls">
        <button 
          class="cart-item-button-decrease"
          onclick="updateQuantity('${item.name}', -1)"
          aria-label="Decrease quantity"
        >
          <img src="./assets/images/icon-decrement-quantity.svg" alt="">
        </button>
        <span class="cart-item-quantity">${item.quantity}</span>
        <button 
          class="cart-item-button"
          onclick="updateQuantity('${item.name}', 1)"
          aria-label="Increase quantity"
        >
          <img src="./assets/images/icon-increment-quantity.svg" alt="">
        </button>
        <button 
          class="cart-item-remove"
          onclick="RemoveFromCart('${item.name}')"
          aria-label="Remove item"
        >
          <img src="./assets/images/wrong.png" alt="">
        </button>
      </div>
    </div>
  `).join('');

  cartTotal.textContent = `$${totalPrice.toFixed(2)}`;
}

function checkDiscount() {
  var discountcode = orderDiscount.value;
  if (discountcode === discountCodes[0]) {
    appliedDiscount = 0.10;
    discountCodeStatus.innerText="the discount code 10% is correct";
    discountCodeStatus.style.color= "green";
  } else if (discountcode === discountCodes[1]) {
    appliedDiscount = 0.25;
    discountCodeStatus.innerText="the discount code 25% is correct";
    discountCodeStatus.style.color= "green";
  } else {
    appliedDiscount = 0;
    discountCodeStatus.innerText="the discount code is invalid";
    discountCodeStatus.style.color= "red";
  }

  updateCartDisplay(appliedDiscount);
}

function confirmOrder() {
  if (cart.length === 0) return;
  
  orderPopup.style.display = 'flex';
  document.body.style.overflow = 'hidden';
}

function startNewOrder() {
  cart = [];
  updateCartDisplay();
  closeorderPopup();
}

function closeorderPopup() {
  orderPopup.style.display = 'none';
  document.body.style.overflow = 'auto';
}

async function generateCartPDF() {
  const { jsPDF } = window.jspdf;
  const documnet = new jsPDF();

  let distance = 10;

  documnet.setFontSize(16);
  documnet.text("Order Receipt", 80, distance);
  distance += 10;

  documnet.setFontSize(12);
  cart.forEach((item, index) => {
    documnet.text(`${index + 1}. ${item.name}`, 10, distance);
    documnet.text(`$${item.price.toFixed(2)} x ${item.quantitdistance}`, 100, distance);
    distance += 8;
  });

  distance += 5;

  let subtotal = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
  let discountAmount = subtotal * appliedDiscount;
  let total = subtotal - discountAmount;

  documnet.setFontSize(12);
  documnet.text(`Subtotal: $${subtotal.toFixed(2)}`, 10, distance); 
  distance += 7;
  if (appliedDiscount > 0) {
    documnet.text(`Discount (${(appliedDiscount * 100)}%): -$${discountAmount.toFixed(2)}`, 10, distance);
    distance += 7;
  }
  documnet.setFontSize(14);
  documnet.text(`Total: $${total.toFixed(2)}`, 10, distance);

  const pdfBlob = documnet.output("blob");
  const blobUrl = URL.createObjectURL(pdfBlob);
  window.open(blobUrl);
}


