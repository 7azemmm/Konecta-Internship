
import { cart, setCart } from './globalVariables.js';
import { orderPopup } from './domElements.js';
import { updateCartDisplay } from './Services/cartService.js';

export function confirmOrder() {
  if (cart.length === 0) return;
  orderPopup.style.display = 'flex';
  document.body.style.overflow = 'hidden';
}

export function closeorderPopup() {
  orderPopup.style.display = 'none';
  document.body.style.overflow = 'auto';
}

export function startNewOrder() {
  setCart([]);
  updateCartDisplay();
  closeorderPopup();
}
