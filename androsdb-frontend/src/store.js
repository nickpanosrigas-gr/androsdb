import { create } from 'zustand';

export const useStore = create((set) => ({
    timelineYear: 1845,
    setTimelineYear: (year) => set({ timelineYear: year }),

    searchQuery: '',
    setSearchQuery: (query) => set({ searchQuery: query }),

    activeTab: 'persons',
    setActiveTab: (tab) => set({ activeTab: tab })
}));