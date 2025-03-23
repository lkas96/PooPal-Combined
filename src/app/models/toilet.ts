export interface Toilet {
    id: string;
    name: string;
    district: string;
    type: string;
    rating: number;
    
}

export interface NearestToilet {
    id: string;
    name: string;
    district: string;
    type: string;
    rating: number;
    distance: number;
}
