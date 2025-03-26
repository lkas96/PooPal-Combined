export interface Toilet {
    id: string;
    name: string;
    district: string;
    type: string;
    rating: number;
    placeId: string;
    
}

export interface NearestToilet {
    id: string;
    name: string;
    district: string;
    type: string;
    rating: number;
    distance: number;
    placeId: string;
}
