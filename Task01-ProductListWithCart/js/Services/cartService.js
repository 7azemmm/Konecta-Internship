// cartService.js
import { products, cart, appliedDiscount, setCart } from '../globalVariables.js';
import {
  cartCount, cartEmpty, cartItems, cartFooter, cartTotal
} from '../domElements.js';
import { renderProducts } from './productService.js';


export function addToCart(productName) {
  const product = products.find(p => p.name === productName);
  if (!product) return;

  const existingItem = cart.find(item => item.name === productName);
  

  if (existingItem) {
    existingItem.quantity += 1;
  } else {
    cart.push({ ...product, quantity: 1 });
  }

  updateCartDisplay();
  renderProducts();
}

export function updateQuantity(productName, change) {
  const item = cart.find(item => item.name === productName);
  if (!item) return;

  const newQuantity = item.quantity + change;
  

  if (newQuantity <= 0) {
    RemoveFromCart(productName);
  } else {
    item.quantity = newQuantity;
  }
  
  updateCartDisplay();
  renderProducts();
}

export function RemoveFromCart(productName) {
  setCart(cart.filter(item => item.name !== productName));
  updateCartDisplay();
  renderProducts();
}

export function updateCartDisplay(discountPercentage = appliedDiscount) {
  const totalItems = cart.reduce((sum, item) => sum + item.quantity, 0);
  let totalPrice = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);

  let discountMoney = 0;
  if (discountPercentage) {
    discountMoney = totalPrice * discountPercentage;
    totalPrice -= discountMoney;
  }

  cartCount.textContent = `(${totalItems})`;
  cartEmpty.style.display = cart.length === 0 ? 'block' : 'none';
  cartItems.style.display = cart.length === 0 ? 'none' : 'block';
  cartFooter.style.display = cart.length === 0 ? 'none' : 'block';

  cartItems.innerHTML = cart.map(item => {
    const originalProduct = products.find(p => p.name === item.name);
    const remainingStock = originalProduct.quantity - item.quantity;
    
    return `
    <div class="cart-item">
      <img src="${item.image.thumbnail}" alt="${item.name}" class="cart-item-image">
      <div class="cart-item-details">
        <h4 class="cart-item-name">${item.name}</h4>
        <p class="cart-item-price">$${item.price.toFixed(2)}</p>
        <p class="cart-item-stock">
          Stock: ${remainingStock} available
        </p>
      </div>
      <div class="cart-item-controls">
        <button class="cart-item-button-decrease" onclick="updateQuantity('${item.name}', -1)">
          <img src="./assets/images/icon-decrement-quantity.svg" alt="">
        </button>
        <span class="cart-item-quantity">${item.quantity}</span>
        <button class="cart-item-button" onclick="updateQuantity('${item.name}', 1)">
          <img src="./assets/images/icon-increment-quantity.svg" alt="">
        </button>
        <button class="cart-item-remove" onclick="RemoveFromCart('${item.name}')">
          <img src="./assets/images/wrong.png" alt="">
        </button>
      </div>
    </div>`;
  }).join('');

  cartTotal.textContent = `$${totalPrice.toFixed(2)}`;
}
