import {confirmOrder, startNewOrder, closeorderPopup} from './UiHandlers.js';
import { checkDiscount } from './Services/discountService.js';
import { generateCartPDF } from './Services/pdfService.js';
import { loadProducts , renderProducts  } from './Services/productService.js';
import { addToCart , RemoveFromCart , updateQuantity } from './Services/cartService.js';
import {
  confirmOrderBtn, startNewOrderBtn, orderPopupOverlay,
  orderDiscountBtn, printPdf
} from './domElements.js';

window.addToCart = window.addToCart || addToCart;
window.RemoveFromCart = window.RemoveFromCart || RemoveFromCart;
window.updateQuantity = window.updateQuantity || updateQuantity;

document.addEventListener('DOMContentLoaded', async () => {
  await loadProducts();
  renderProducts();
});

confirmOrderBtn.addEventListener('click', confirmOrder);
startNewOrderBtn.addEventListener('click', startNewOrder);
orderPopupOverlay.addEventListener('click', closeorderPopup);
orderDiscountBtn.addEventListener('click', checkDiscount);
printPdf.addEventListener('click', generateCartPDF);


