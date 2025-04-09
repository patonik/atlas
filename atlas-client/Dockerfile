# Build Stage: Use Node.js 18 Alpine to build the Vue app
FROM node:18-alpine AS builder

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json (if exists)

COPY package.json ./
COPY package-lock.json* ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .
RUN ls -la /app
# Build the app for production
RUN npm run build

# Production Stage: Use Nginx Alpine to serve the app
FROM nginx:alpine

# Copy the built files from the builder stage
COPY --from=builder /app/dist /usr/share/nginx/html

# Copy custom Nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start Nginx (default command in nginx:alpine image)
CMD ["nginx", "-g", "daemon off;"]