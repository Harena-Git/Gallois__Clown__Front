// Configuration API
const API_BASE_URL = 'http://localhost:8081/frontoffice/api/public';

// Variables globales
let hotels = [];
let reservations = [];

// Initialisation au chargement
document.addEventListener('DOMContentLoaded', function() {
    loadHotels();
    populateHotelSelects();
});

// =================== AFFICHAGE DES SECTIONS ===================

function showSection(sectionId) {
    // Masquer toutes les sections
    document.querySelectorAll('.section').forEach(section => {
        section.classList.add('hidden');
    });
    document.getElementById('home').classList.add('hidden');
    
    // Afficher la section demandée
    if (sectionId !== 'home') {
        document.getElementById(sectionId).classList.remove('hidden');
    } else {
        document.getElementById('home').classList.remove('hidden');
    }
}

function showHotels() {
    showSection('hotels-section');
    loadHotels();
}

function showReservations() {
    showSection('reservations-section');
    populateHotelSelects();
}

function showSearch() {
    showSection('search-section');
    populateSearchFilters();
}

// =================== CHARGEMENT DES HOTELS ===================

async function loadHotels() {
    try {
        const response = await fetch(`${API_BASE_URL}/hotels`);
        if (!response.ok) throw new Error('Erreur lors du chargement des hôtels');
        
        hotels = await response.json();
        displayHotels(hotels);
    } catch (error) {
        console.error('Erreur:', error);
        showMessage('Erreur lors du chargement des hôtels', 'error');
    }
}

function displayHotels(hotelList) {
    const hotelsList = document.getElementById('hotels-list');
    hotelsList.innerHTML = '';
    
    if (hotelList.length === 0) {
        hotelsList.innerHTML = '<p>Aucun hôtel disponible</p>';
        return;
    }
    
    hotelList.forEach(hotel => {
        const hotelCard = createHotelCard(hotel);
        hotelsList.appendChild(hotelCard);
    });
}

function createHotelCard(hotel) {
    const card = document.createElement('div');
    card.className = 'hotel-card';
    card.innerHTML = `
        <div class="hotel-card-header">
            <h3>${hotel.nom}</h3>
        </div>
        <div class="hotel-card-body">
            <p>${hotel.description || 'Sans description'}</p>
            <div class="hotel-info">
                <p>📍 ${hotel.ville} ${hotel.codePostal ? '- ' + hotel.codePostal : ''}</p>
                ${hotel.adresse ? `<p>🏠 ${hotel.adresse}</p>` : ''}
                ${hotel.telephone ? `<p>📞 ${hotel.telephone}</p>` : ''}
                ${hotel.nombreChambres ? `<p>🛏️  ${hotel.nombreChambres} chambres</p>` : ''}
            </div>
            ${hotel.prixMoyenNuit ? `<p class="hotel-price">💰 ${hotel.prixMoyenNuit.toFixed(2)}€ / nuit</p>` : ''}
        </div>
    `;
    return card;
}

// =================== GESTION DES RESERVATIONS ===================

async function createReservation(event) {
    event.preventDefault();
    
    const hotelId = document.getElementById('hotel-select').value;
    const arrivalDate = document.getElementById('arrival-date').value;
    const departureDate = document.getElementById('departure-date').value;
    const passengers = document.getElementById('passengers').value;
    const clientId = document.getElementById('client-id').value;
    const notes = document.getElementById('notes').value;
    
    if (!hotelId || !arrivalDate || !passengers) {
        showMessage('Veuillez remplir tous les champs obligatoires', 'error');
        return;
    }
    
    const reservation = {
        hotel: { id: parseInt(hotelId) },
        dateHeureArrive: arrivalDate,
        dateHeureDepart: departureDate || null,
        nombrePassager: parseInt(passengers),
        idClient: clientId ? parseInt(clientId) : null,
        notes: notes
    };
    
    try {
        const response = await fetch(`${API_BASE_URL}/reservations`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(reservation)
        });
        
        if (!response.ok) throw new Error('Erreur lors de la création de la réservation');
        
        const result = await response.json();
        showMessage('✅ Réservation créée avec succès! ID: ' + result.id, 'success');
        document.querySelector('.reservation-form').reset();
    } catch (error) {
        console.error('Erreur:', error);
        showMessage('Erreur lors de la création de la réservation', 'error');
    }
}

async function searchReservations() {
    const date = document.getElementById('search-date').value;
    const hotelId = document.getElementById('search-hotel').value;
    
    if (!date && !hotelId) {
        showMessage('Sélectionnez au moins une date ou un hôtel', 'error');
        return;
    }
    
    try {
        let url = `${API_BASE_URL}/reservations`;
        if (date && hotelId) {
            url = `${API_BASE_URL}/reservations/search/date?date=${date}`;
        } else if (date) {
            url = `${API_BASE_URL}/reservations/search/date?date=${date}`;
        } else if (hotelId) {
            url = `${API_BASE_URL}/reservations/search/hotel/${hotelId}`;
        }
        
        const response = await fetch(url);
        if (!response.ok) throw new Error('Erreur lors de la recherche');
        
        const results = await response.json();
        displaySearchResults(results);
    } catch (error) {
        console.error('Erreur:', error);
        showMessage('Erreur lors de la recherche', 'error');
    }
}

function displaySearchResults(results) {
    const resultsDiv = document.getElementById('search-results');
    resultsDiv.innerHTML = '';
    
    if (results.length === 0) {
        resultsDiv.innerHTML = '<p>Aucune réservation trouvée</p>';
        return;
    }
    
    results.forEach(reservation => {
        const item = document.createElement('div');
        item.className = 'result-item';
        item.innerHTML = `
            <h4>Réservation #${reservation.id}</h4>
            <p><strong>Hôtel:</strong> ${reservation.hotel?.nom || 'N/A'}</p>
            <p><strong>Date d'arrivée:</strong> ${new Date(reservation.dateHeureArrive).toLocaleString('fr-FR')}</p>
            <p><strong>Passagers:</strong> ${reservation.nombrePassager}</p>
            <p><strong>Statut:</strong> <span style="color: ${getStatusColor(reservation.statut)}">${reservation.statut}</span></p>
            ${reservation.notes ? `<p><strong>Notes:</strong> ${reservation.notes}</p>` : ''}
        `;
        resultsDiv.appendChild(item);
    });
}

function getStatusColor(status) {
    const colors = {
        'EN_ATTENTE': '#f39c12',
        'CONFIRMEE': '#27ae60',
        'ANNULEE': '#e74c3c'
    };
    return colors[status] || '#333';
}

// =================== REMPLISSAGE DES SELECTS ===================

async function populateHotelSelects() {
    try {
        const response = await fetch(`${API_BASE_URL}/hotels`);
        if (!response.ok) throw new Error('Erreur lors du chargement des hôtels');
        
        const hotelList = await response.json();
        
        const hotelSelect = document.getElementById('hotel-select');
        const searchSelect = document.getElementById('search-hotel');
        
        hotelList.forEach(hotel => {
            // Pour le formulaire de réservation
            const option1 = document.createElement('option');
            option1.value = hotel.id;
            option1.textContent = hotel.nom;
            hotelSelect.appendChild(option1);
            
            // Pour la recherche
            const option2 = document.createElement('option');
            option2.value = hotel.id;
            option2.textContent = hotel.nom;
            searchSelect.appendChild(option2);
        });
    } catch (error) {
        console.error('Erreur:', error);
    }
}

function populateSearchFilters() {
    populateHotelSelects();
}

// =================== MESSAGES ===================

function showMessage(message, type) {
    const messageDiv = document.getElementById('reservation-message');
    messageDiv.textContent = message;
    messageDiv.className = `message ${type}`;
    
    setTimeout(() => {
        messageDiv.className = 'message';
    }, 5000);
}

// =================== UTILITAIRES ===================

function formatDate(dateString) {
    return new Date(dateString).toLocaleString('fr-FR');
}
