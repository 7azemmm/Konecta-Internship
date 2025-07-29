let products = [];
let cart = [];

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
    console.error('Error loading products:', error);
    throw error;
  }
}

// Render products grid
function renderProducts() {
  productsGrid.innerHTML = products.map(product => `
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
          <img src="./assets/images/icon-add-to-cart.svg" alt="add-to-cart">
          Add to Cart
        </button>
      </div>
    </article>
  `).join('');
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
  showAddToCartFeedback(productName);
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


function updateCartDisplay() {
  const totalItems = cart.reduce((sum, item) => sum + item.quantity, 0);
  const totalPrice = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);

  // Update cart count
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
          <img src="./assets/images/icon-remove-item.svg" alt="">
        </button>
      </div>
    </div>
  `).join('');

  cartTotal.textContent = `$${totalPrice.toFixed(2)}`;
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


confirmOrderBtn.addEventListener('click', confirmOrder);
startNewOrderBtn.addEventListener('click', startNewOrder);
orderPopupOverlay.addEventListener('click', closeorderPopup);



document.addEventListener('DOMContentLoaded', () => {
  init();
  
});



