// productService.js
import { setProducts, products, cart } from '../globalVariables.js';
import { productsGrid } from '../domElements.js';

export async function loadProducts() {
  const response = await fetch('./data.json');
  const data = await response.json();
  setProducts(data);
}

export function renderProducts() {
  productsGrid.innerHTML = products.map(product => {
    const cartItem = cart.find(item => item.name === product.name);
    const cartQuantity = cartItem ? cartItem.quantity : 0;
    const availableStock = product.quantity - cartQuantity;
    const isOutOfStock = availableStock <= 0;

    return `
      <article class="product-card">
        <img src="${product.image.desktop}" alt="${product.name}" class="product-card-image" loading="lazy">
        <div class="product-card-content">
          <h3 class="product-card-name">${product.name}</h3>
          <p class="product-card-category">${product.category}</p>
          <p class="product-card-price">$${product.price.toFixed(2)}</p>
          <p class="product-card-stock ${isOutOfStock ? 'out-of-stock' : 'in-stock'}">
            ${isOutOfStock ? 'Out of Stock' : `${availableStock} available`}
          </p>
          <button class="product-card-button" 
                  onclick="addToCart('${product.name}')" 
                  ${isOutOfStock ? 'disabled' : ''}>
            <img src="./assets/images/add-to-cart.png" alt="add-to-cart"> 
            ${isOutOfStock ? 'Out of Stock' : 'Add to Cart'}
          </button>
        </div>
      </article>`;
  }).join('');
}
