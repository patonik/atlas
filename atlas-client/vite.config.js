import {defineConfig} from 'vite';
import vue from '@vitejs/plugin-vue';

export default defineConfig({
    plugins: [vue()],
    build: {
        outDir: 'dist',
        manifest: true,
        emptyOutDir: true,
    },
    server: {
        proxy: {
            '/api': {
                target: 'http://atlas-server:9090',
                changeOrigin: true,
                rewrite: (path) => path, // Keep /api prefix
            },
        },
    }
});